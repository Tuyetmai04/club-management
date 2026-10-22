package com.clubmanagement.service.impl;

import com.clubmanagement.entity.Meeting;
import com.clubmanagement.repository.MeetingRepository;
import com.clubmanagement.service.MeetingService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class MeetingServiceImpl implements MeetingService {

    private final MeetingRepository meetingRepository;

    public MeetingServiceImpl(MeetingRepository meetingRepository) {
        this.meetingRepository = meetingRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Meeting> getAllMeetings() {
        return meetingRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Meeting> searchMeetings(String keyword) {
        return meetingRepository.searchMeetings(keyword);
    }

    @Override
    @Transactional(readOnly = true)
    public Meeting getMeetingById(Long id) {
        return meetingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy cuộc họp với ID: " + id));
    }

    @Override
    public Meeting saveMeeting(Meeting meeting) {
        if (meeting.getId() == null && existsByMeetingCode(meeting.getMeetingCode())) {
            throw new IllegalArgumentException("Mã cuộc họp '" + meeting.getMeetingCode() + "' đã tồn tại!");
        }
        return meetingRepository.save(meeting);
    }

    @Override
    public Meeting updateMeeting(Long id, Meeting meetingDetails) {
        Meeting existing = getMeetingById(id);
        if (isMeetingCodeDuplicate(meetingDetails.getMeetingCode(), id)) {
            throw new IllegalArgumentException("Mã cuộc họp '" + meetingDetails.getMeetingCode() + "' đã thuộc về cuộc họp khác!");
        }
        existing.setMeetingCode(meetingDetails.getMeetingCode());
        existing.setTitle(meetingDetails.getTitle());
        existing.setLocation(meetingDetails.getLocation());
        existing.setFormat(meetingDetails.getFormat());
        existing.setMeetingTime(meetingDetails.getMeetingTime());
        existing.setHost(meetingDetails.getHost());
        existing.setContent(meetingDetails.getContent());
        return meetingRepository.save(existing);
    }

    @Override
    public void deleteMeeting(Long id) {
        Meeting meeting = getMeetingById(id);
        meetingRepository.delete(meeting);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByMeetingCode(String meetingCode) {
        return meetingRepository.existsByMeetingCode(meetingCode);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isMeetingCodeDuplicate(String meetingCode, Long id) {
        if (id == null) {
            return meetingRepository.existsByMeetingCode(meetingCode);
        }
        return meetingRepository.existsByMeetingCodeAndIdNot(meetingCode, id);
    }

    @Override
    @Transactional(readOnly = true)
    public long countTotalMeetings() {
        return meetingRepository.count();
    }
}
