package com.clubmanagement.repository;

import com.clubmanagement.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

    Optional<Event> findByEventCode(String eventCode);

    boolean existsByEventCode(String eventCode);

    boolean existsByEventCodeAndIdNot(String eventCode, Long id);

    @Query("SELECT e FROM Event e WHERE :keyword IS NULL OR LOWER(e.title) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(e.eventCode) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(e.location) LIKE LOWER(CONCAT('%', :keyword, '%')) ORDER BY e.startTime DESC")
    List<Event> searchEvents(@Param("keyword") String keyword);
}
