package com.clubmanagement.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "members")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Mã thành viên không được để trống")
    @Size(max = 20, message = "Mã thành viên tối đa 20 ký tự")
    @Column(name = "member_code", nullable = false, unique = true, length = 20)
    private String memberCode;

    @NotBlank(message = "Họ và tên không được để trống")
    @Size(max = 100, message = "Họ và tên tối đa 100 ký tự")
    @Column(name = "full_name", nullable = false, length = 100)
    private String fullName;

    @NotNull(message = "Ngày sinh không được để trống")
    @Past(message = "Ngày sinh phải là một ngày trong quá khứ")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "date_of_birth", nullable = false)
    private LocalDate dateOfBirth;

    @NotBlank(message = "Email không được để trống")
    @Email(message = "Email không đúng định dạng")
    @Column(name = "email", nullable = false, length = 100)
    private String email;

    @NotBlank(message = "Số điện thoại không được để trống")
    @Pattern(regexp = "^(0|\\+84)[3|5|7|8|9][0-9]{8}$", message = "Số điện thoại không hợp lệ (VD: 0912345678)")
    @Column(name = "phone", nullable = false, length = 15)
    private String phone;

    @NotNull(message = "Ngày tham gia không được để trống")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "join_date", nullable = false)
    private LocalDate joinDate;

    @NotBlank(message = "Chức vụ không được để trống")
    @Column(name = "position", nullable = false, length = 50)
    private String position;

    @NotBlank(message = "Trạng thái không được để trống")
    @Column(name = "status", nullable = false, length = 30)
    private String status; // HOAT_DONG, TAM_NGHI, DA_ROI

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Contribution> contributions = new ArrayList<>();

    public Member() {
    }

    public Member(String memberCode, String fullName, LocalDate dateOfBirth, String email, String phone, LocalDate joinDate, String position, String status) {
        this.memberCode = memberCode;
        this.fullName = fullName;
        this.dateOfBirth = dateOfBirth;
        this.email = email;
        this.phone = phone;
        this.joinDate = joinDate;
        this.position = position;
        this.status = status;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMemberCode() {
        return memberCode;
    }

    public void setMemberCode(String memberCode) {
        this.memberCode = memberCode;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public LocalDate getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(LocalDate joinDate) {
        this.joinDate = joinDate;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Contribution> getContributions() {
        return contributions;
    }

    public void setContributions(List<Contribution> contributions) {
        this.contributions = contributions;
    }

    public String getStatusBadgeClass() {
        if ("Đang hoạt động".equalsIgnoreCase(status) || "HOAT_DONG".equalsIgnoreCase(status)) {
            return "bg-success";
        } else if ("Tạm nghỉ".equalsIgnoreCase(status) || "TAM_NGHI".equalsIgnoreCase(status)) {
            return "bg-warning text-dark";
        } else {
            return "bg-secondary";
        }
    }
}
