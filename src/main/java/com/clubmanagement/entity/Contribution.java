package com.clubmanagement.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "contributions")
public class Contribution {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Mã đóng góp không được để trống")
    @Size(max = 20, message = "Mã đóng góp tối đa 20 ký tự")
    @Column(name = "contribution_code", nullable = false, unique = true, length = 20)
    private String contributionCode;

    @NotNull(message = "Vui lòng chọn thành viên đóng góp")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @NotBlank(message = "Nội dung đóng góp không được để trống")
    @Size(max = 200, message = "Nội dung đóng góp tối đa 200 ký tự")
    @Column(name = "title", nullable = false, length = 200)
    private String title;

    @NotNull(message = "Số tiền không được để trống")
    @DecimalMin(value = "1000", message = "Số tiền tối thiểu là 1,000 VNĐ")
    @Column(name = "amount", nullable = false, precision = 15, scale = 2)
    private BigDecimal amount;

    @NotNull(message = "Ngày đóng góp không được để trống")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "contribution_date", nullable = false)
    private LocalDate contributionDate;

    @NotBlank(message = "Người thu/nhận không được để trống")
    @Column(name = "collector", nullable = false, length = 100)
    private String collector;

    @Column(name = "note", length = 500)
    private String note;

    public Contribution() {
    }

    public Contribution(String contributionCode, Member member, String title, BigDecimal amount, LocalDate contributionDate, String collector, String note) {
        this.contributionCode = contributionCode;
        this.member = member;
        this.title = title;
        this.amount = amount;
        this.contributionDate = contributionDate;
        this.collector = collector;
        this.note = note;
    }

    // Getters & Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContributionCode() {
        return contributionCode;
    }

    public void setContributionCode(String contributionCode) {
        this.contributionCode = contributionCode;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public LocalDate getContributionDate() {
        return contributionDate;
    }

    public void setContributionDate(LocalDate contributionDate) {
        this.contributionDate = contributionDate;
    }

    public String getCollector() {
        return collector;
    }

    public void setCollector(String collector) {
        this.collector = collector;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
