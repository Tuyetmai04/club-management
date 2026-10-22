package com.clubmanagement.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Entity
@Table(name = "meetings")
public class Meeting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Mã cuộc họp không được để trống")
    @Size(max = 20, message = "Mã cuộc họp tối đa 20 ký tự")
    @Column(name = "meeting_code", nullable = false, unique = true, length = 20)
    private String meetingCode;

    @NotBlank(message = "Tên cuộc họp không được để trống")
    @Size(max = 200, message = "Tên cuộc họp tối đa 200 ký tự")
    @Column(name = "title", nullable = false, length = 200)
    private String title;

    @NotBlank(message = "Địa điểm không được để trống")
    @Column(name = "location", nullable = false, length = 200)
    private String location;

    @NotBlank(message = "Hình thức họp không được để trống")
    @Column(name = "format", nullable = false, length = 50)
    private String format; // Trực tiếp, Trực tuyến

    @NotNull(message = "Ngày giờ họp không được để trống")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    @Column(name = "meeting_time", nullable = false)
    private LocalDateTime meetingTime;

    @NotBlank(message = "Người chủ trì không được để trống")
    @Column(name = "host", nullable = false, length = 100)
    private String host;

    @Column(name = "content", length = 2000)
    private String content;

    public Meeting() {
    }

    public Meeting(String meetingCode, String title, String location, String format, LocalDateTime meetingTime, String host, String content) {
        this.meetingCode = meetingCode;
        this.title = title;
        this.location = location;
        this.format = format;
        this.meetingTime = meetingTime;
        this.host = host;
        this.content = content;
    }

    // Getters & Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMeetingCode() {
        return meetingCode;
    }

    public void setMeetingCode(String meetingCode) {
        this.meetingCode = meetingCode;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public LocalDateTime getMeetingTime() {
        return meetingTime;
    }

    public void setMeetingTime(LocalDateTime meetingTime) {
        this.meetingTime = meetingTime;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getFormatBadgeClass() {
        if ("Trực tuyến".equalsIgnoreCase(format) || "ONLINE".equalsIgnoreCase(format)) {
            return "bg-primary";
        } else {
            return "bg-info text-dark";
        }
    }
}
