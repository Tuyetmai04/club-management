package com.clubmanagement.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Entity
@Table(name = "events")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Mã sự kiện không được để trống")
    @Size(max = 20, message = "Mã sự kiện tối đa 20 ký tự")
    @Column(name = "event_code", nullable = false, unique = true, length = 20)
    private String eventCode;

    @NotBlank(message = "Tên sự kiện không được để trống")
    @Size(max = 200, message = "Tên sự kiện tối đa 200 ký tự")
    @Column(name = "title", nullable = false, length = 200)
    private String title;

    @NotNull(message = "Thời gian bắt đầu không được để trống")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    @Column(name = "start_time", nullable = false)
    private LocalDateTime startTime;

    @NotNull(message = "Thời gian kết thúc không được để trống")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    @Column(name = "end_time", nullable = false)
    private LocalDateTime endTime;

    @NotNull(message = "Số lượng người tham gia không được để trống")
    @Min(value = 1, message = "Số lượng người tham gia tối thiểu là 1")
    @Column(name = "max_participants", nullable = false)
    private Integer maxParticipants;

    @NotBlank(message = "Người chủ trì không được để trống")
    @Column(name = "host", nullable = false, length = 100)
    private String host;

    @NotBlank(message = "Địa điểm không được để trống")
    @Column(name = "location", nullable = false, length = 200)
    private String location;

    @Column(name = "description", length = 1000)
    private String description;

    public Event() {
    }

    public Event(String eventCode, String title, LocalDateTime startTime, LocalDateTime endTime, Integer maxParticipants, String host, String location, String description) {
        this.eventCode = eventCode;
        this.title = title;
        this.startTime = startTime;
        this.endTime = endTime;
        this.maxParticipants = maxParticipants;
        this.host = host;
        this.location = location;
        this.description = description;
    }

    // Getters & Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEventCode() {
        return eventCode;
    }

    public void setEventCode(String eventCode) {
        this.eventCode = eventCode;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public Integer getMaxParticipants() {
        return maxParticipants;
    }

    public void setMaxParticipants(Integer maxParticipants) {
        this.maxParticipants = maxParticipants;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatusText() {
        LocalDateTime now = LocalDateTime.now();
        if (now.isBefore(startTime)) {
            return "Sắp diễn ra";
        } else if (now.isAfter(endTime)) {
            return "Đã kết thúc";
        } else {
            return "Đang diễn ra";
        }
    }

    public String getStatusBadgeClass() {
        LocalDateTime now = LocalDateTime.now();
        if (now.isBefore(startTime)) {
            return "bg-info text-dark";
        } else if (now.isAfter(endTime)) {
            return "bg-secondary";
        } else {
            return "bg-success";
        }
    }
}
