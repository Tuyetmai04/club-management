package com.clubmanagement.service;

import com.clubmanagement.entity.Event;

import java.util.List;

public interface EventService {

    List<Event> getAllEvents();

    List<Event> searchEvents(String keyword);

    Event getEventById(Long id);

    Event saveEvent(Event event);

    Event updateEvent(Long id, Event eventDetails);

    void deleteEvent(Long id);

    boolean existsByEventCode(String eventCode);

    boolean isEventCodeDuplicate(String eventCode, Long id);

    long countTotalEvents();
}
