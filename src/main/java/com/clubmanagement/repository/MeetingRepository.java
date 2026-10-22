package com.clubmanagement.repository;

import com.clubmanagement.entity.Meeting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MeetingRepository extends JpaRepository<Meeting, Long> {

    Optional<Meeting> findByMeetingCode(String meetingCode);

    boolean existsByMeetingCode(String meetingCode);

    boolean existsByMeetingCodeAndIdNot(String meetingCode, Long id);

    @Query("SELECT m FROM Meeting m WHERE :keyword IS NULL OR LOWER(m.title) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(m.meetingCode) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(m.location) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(m.host) LIKE LOWER(CONCAT('%', :keyword, '%')) ORDER BY m.meetingTime DESC")
    List<Meeting> searchMeetings(@Param("keyword") String keyword);
}
