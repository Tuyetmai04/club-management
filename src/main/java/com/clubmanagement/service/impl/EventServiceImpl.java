package com.clubmanagement.service.impl;

import com.clubmanagement.entity.Event;
import com.clubmanagement.repository.EventRepository;
import com.clubmanagement.service.EventService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;

    public EventServiceImpl(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Event> searchEvents(String keyword) {
        return eventRepository.searchEvents(keyword);
    }

    @Override
    @Transactional(readOnly = true)
    public Event getEventById(Long id) {
        return eventRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sự kiện với ID: " + id));
    }

    @Override
    public Event saveEvent(Event event) {
        if (event.getId() == null && existsByEventCode(event.getEventCode())) {
            throw new IllegalArgumentException("Mã sự kiện '" + event.getEventCode() + "' đã tồn tại!");
        }
        if (event.getEndTime() != null && event.getStartTime() != null && event.getEndTime().isBefore(event.getStartTime())) {
            throw new IllegalArgumentException("Thời gian kết thúc phải sau thời gian bắt đầu!");
        }
        return eventRepository.save(event);
    }

    @Override
    public Event updateEvent(Long id, Event eventDetails) {
        Event existing = getEventById(id);
        if (isEventCodeDuplicate(eventDetails.getEventCode(), id)) {
            throw new IllegalArgumentException("Mã sự kiện '" + eventDetails.getEventCode() + "' đã thuộc về sự kiện khác!");
        }
        if (eventDetails.getEndTime() != null && eventDetails.getStartTime() != null && eventDetails.getEndTime().isBefore(eventDetails.getStartTime())) {
            throw new IllegalArgumentException("Thời gian kết thúc phải sau thời gian bắt đầu!");
        }
        existing.setEventCode(eventDetails.getEventCode());
        existing.setTitle(eventDetails.getTitle());
        existing.setStartTime(eventDetails.getStartTime());
        existing.setEndTime(eventDetails.getEndTime());
        existing.setMaxParticipants(eventDetails.getMaxParticipants());
        existing.setHost(eventDetails.getHost());
        existing.setLocation(eventDetails.getLocation());
        existing.setDescription(eventDetails.getDescription());
        return eventRepository.save(existing);
    }

    @Override
    public void deleteEvent(Long id) {
        Event event = getEventById(id);
        eventRepository.delete(event);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByEventCode(String eventCode) {
        return eventRepository.existsByEventCode(eventCode);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isEventCodeDuplicate(String eventCode, Long id) {
        if (id == null) {
            return eventRepository.existsByEventCode(eventCode);
        }
        return eventRepository.existsByEventCodeAndIdNot(eventCode, id);
    }

    @Override
    @Transactional(readOnly = true)
    public long countTotalEvents() {
        return eventRepository.count();
    }
}
