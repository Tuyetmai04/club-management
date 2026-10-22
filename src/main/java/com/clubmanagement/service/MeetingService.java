package com.clubmanagement.service;

import com.clubmanagement.entity.Meeting;

import java.util.List;

public interface MeetingService {

    List<Meeting> getAllMeetings();

    List<Meeting> searchMeetings(String keyword);

    Meeting getMeetingById(Long id);

    Meeting saveMeeting(Meeting meeting);

    Meeting updateMeeting(Long id, Meeting meetingDetails);

    void deleteMeeting(Long id);

    boolean existsByMeetingCode(String meetingCode);

    boolean isMeetingCodeDuplicate(String meetingCode, Long id);

    long countTotalMeetings();
}
