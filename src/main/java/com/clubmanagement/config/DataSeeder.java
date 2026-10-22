package com.clubmanagement.config;

import com.clubmanagement.entity.Contribution;
import com.clubmanagement.entity.Event;
import com.clubmanagement.entity.Meeting;
import com.clubmanagement.entity.Member;
import com.clubmanagement.repository.ContributionRepository;
import com.clubmanagement.repository.EventRepository;
import com.clubmanagement.repository.MeetingRepository;
import com.clubmanagement.repository.MemberRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class DataSeeder implements CommandLineRunner {

    private final MemberRepository memberRepository;
    private final EventRepository eventRepository;
    private final ContributionRepository contributionRepository;
    private final MeetingRepository meetingRepository;

    public DataSeeder(MemberRepository memberRepository,
                      EventRepository eventRepository,
                      ContributionRepository contributionRepository,
                      MeetingRepository meetingRepository) {
        this.memberRepository = memberRepository;
        this.eventRepository = eventRepository;
        this.contributionRepository = contributionRepository;
        this.meetingRepository = meetingRepository;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        // Only seed data if database is empty to prevent duplicate insertion
        if (memberRepository.count() == 0) {
            seedMembers();
        }

        if (eventRepository.count() == 0) {
            seedEvents();
        }

        if (meetingRepository.count() == 0) {
            seedMeetings();
        }

        if (contributionRepository.count() == 0) {
            seedContributions();
        }
    }

    private void seedMembers() {
        List<Member> members = new ArrayList<>();
        members.add(new Member("TV001", "Nguyen Van An", LocalDate.of(2001, 5, 15), "an.nguyen@example.com", "0912345678", LocalDate.of(2023, 9, 1), "Chủ nhiệm", "Đang hoạt động"));
        members.add(new Member("TV002", "Tran Thi Mai", LocalDate.of(2002, 8, 20), "mai.tran@example.com", "0987654321", LocalDate.of(2023, 9, 1), "Phó chủ nhiệm", "Đang hoạt động"));
        members.add(new Member("TV003", "Le Hoang Nam", LocalDate.of(2002, 3, 10), "nam.le@example.com", "0903112233", LocalDate.of(2023, 9, 15), "Trưởng ban Truyền thông", "Đang hoạt động"));
        members.add(new Member("TV004", "Pham Minh Tuan", LocalDate.of(2003, 11, 5), "tuan.pham@example.com", "0934556677", LocalDate.of(2023, 10, 1), "Trưởng ban Tài chính", "Đang hoạt động"));
        members.add(new Member("TV005", "Vu Thi Bich", LocalDate.of(2003, 1, 25), "bich.vu@example.com", "0978990011", LocalDate.of(2023, 10, 15), "Thành viên", "Đang hoạt động"));
        members.add(new Member("TV006", "Hoang Van Duc", LocalDate.of(2001, 12, 30), "duc.hoang@example.com", "0961223344", LocalDate.of(2023, 11, 1), "Thành viên", "Tạm nghỉ"));
        members.add(new Member("TV007", "Dang Thu Thao", LocalDate.of(2002, 7, 18), "thao.dang@example.com", "0918776655", LocalDate.of(2024, 1, 10), "Thành viên", "Đang hoạt động"));

        memberRepository.saveAll(members);
    }

    private void seedEvents() {
        List<Event> events = new ArrayList<>();
        events.add(new Event(
                "SK001",
                "Chào tân sinh viên 2026",
                LocalDateTime.of(2026, 10, 20, 18, 0),
                LocalDateTime.of(2026, 10, 20, 21, 30),
                200,
                "Le Hoang Nam",
                "Hội trường A1",
                "Chương trình giao lưu văn nghệ và chào đón các bạn tân sinh viên gia nhập câu lạc bộ."
        ));

        events.add(new Event(
                "SK002",
                "Workshop: Kỹ năng Lập trình Java & Spring Boot",
                LocalDateTime.of(2026, 11, 5, 8, 30),
                LocalDateTime.of(2026, 11, 5, 11, 30),
                80,
                "Nguyen Van An",
                "Phòng Máy 302",
                "Chia sẻ kiến thức chuyên môn về xây dựng ứng dụng web chuẩn doanh nghiệp với Spring Boot."
        ));

        events.add(new Event(
                "SK003",
                "Giải bóng đá thường niên CLB",
                LocalDateTime.of(2026, 12, 1, 7, 0),
                LocalDateTime.of(2026, 12, 1, 17, 0),
                120,
                "Pham Minh Tuan",
                "Sân bóng đá trường",
                "Hoạt động thể thao rèn luyện sức khỏe và thắt chặt tình đoàn kết giữa các thành viên."
        ));

        events.add(new Event(
                "SK004",
                "Tập huấn kỹ năng mềm & Team Building",
                LocalDateTime.of(2026, 9, 15, 8, 0),
                LocalDateTime.of(2026, 9, 15, 17, 0),
                60,
                "Tran Thi Mai",
                "Khu du lịch sinh thái",
                "Sự kiện đã diễn ra thành công với nhiều hoạt động trải nghiệm thực tế."
        ));

        eventRepository.saveAll(events);
    }

    private void seedMeetings() {
        List<Meeting> meetings = new ArrayList<>();
        meetings.add(new Meeting(
                "CH001",
                "Họp Ban chủ nhiệm hàng tháng",
                "Phòng họp CLB B201",
                "Trực tiếp",
                LocalDateTime.of(2026, 10, 15, 19, 0),
                "Nguyen Van An",
                "Đánh giá tiến độ công việc tháng 9 và lập kế hoạch tổ chức sự kiện Chào tân sinh viên."
        ));

        meetings.add(new Meeting(
                "CH002",
                "Họp lập kế hoạch dự trù kinh phí Sự kiện Welcome",
                "Google Meet (online)",
                "Trực tuyến",
                LocalDateTime.of(2026, 10, 18, 20, 0),
                "Pham Minh Tuan",
                "Thảo luận ngân sách đóng góp và tài trợ cho sự kiện sắp tới."
        ));

        meetings.add(new Meeting(
                "CH003",
                "Họp toàn thể thành viên Quý IV/2026",
                "Hội trường B3",
                "Trực tiếp",
                LocalDateTime.of(2026, 11, 1, 14, 0),
                "Tran Thi Mai",
                "Báo cáo tổng kết hoạt động quý III và định hướng hoạt động quý IV."
        ));

        meetingRepository.saveAll(meetings);
    }

    private void seedContributions() {
        List<Member> members = memberRepository.findAll();
        if (members.isEmpty()) return;

        List<Contribution> contributions = new ArrayList<>();
        contributions.add(new Contribution(
                "DG001",
                members.get(0),
                "Đóng quỹ CLB Tháng 10/2026",
                new BigDecimal("100000.00"),
                LocalDate.of(2026, 10, 1),
                "Pham Minh Tuan",
                "Đã thu đủ tiền mặt"
        ));

        contributions.add(new Contribution(
                "DG002",
                members.get(1),
                "Đóng quỹ CLB Tháng 10/2026",
                new BigDecimal("100000.00"),
                LocalDate.of(2026, 10, 2),
                "Pham Minh Tuan",
                "Chuyển khoản ngân hàng"
        ));

        contributions.add(new Contribution(
                "DG003",
                members.get(2),
                "Ủng hộ hoạt động Chào tân sinh viên",
                new BigDecimal("500000.00"),
                LocalDate.of(2026, 10, 5),
                "Pham Minh Tuan",
                "Ủng hộ thêm cho ban truyền thông"
        ));

        contributions.add(new Contribution(
                "DG004",
                members.get(3),
                "Đóng quỹ CLB Tháng 10/2026",
                new BigDecimal("100000.00"),
                LocalDate.of(2026, 10, 1),
                "Nguyen Van An",
                "Nộp trực tiếp"
        ));

        contributions.add(new Contribution(
                "DG005",
                members.get(4),
                "Đóng quỹ CLB Tháng 10/2026",
                new BigDecimal("100000.00"),
                LocalDate.of(2026, 10, 3),
                "Pham Minh Tuan",
                "Chuyển khoản"
        ));

        contributionRepository.saveAll(contributions);
    }
}
