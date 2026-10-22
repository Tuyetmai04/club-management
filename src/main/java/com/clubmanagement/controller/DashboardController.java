package com.clubmanagement.controller;

import com.clubmanagement.service.ContributionService;
import com.clubmanagement.service.EventService;
import com.clubmanagement.service.MeetingService;
import com.clubmanagement.service.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

@Controller
public class DashboardController {

    private final MemberService memberService;
    private final EventService eventService;
    private final ContributionService contributionService;
    private final MeetingService meetingService;

    public DashboardController(MemberService memberService,
                               EventService eventService,
                               ContributionService contributionService,
                               MeetingService meetingService) {
        this.memberService = memberService;
        this.eventService = eventService;
        this.contributionService = contributionService;
        this.meetingService = meetingService;
    }

    @GetMapping({"/", "/dashboard"})
    public String dashboard(Model model) {
        long totalMembers = memberService.countTotalMembers();
        long activeMembers = memberService.countActiveMembers();
        long totalEvents = eventService.countTotalEvents();
        long totalContributions = contributionService.countTotalContributions();
        BigDecimal totalContributionAmount = contributionService.getTotalContributionAmount();
        long totalMeetings = meetingService.countTotalMeetings();

        // Format currency VND
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        String formattedTotalAmount = currencyFormat.format(totalContributionAmount);

        model.addAttribute("totalMembers", totalMembers);
        model.addAttribute("activeMembers", activeMembers);
        model.addAttribute("totalEvents", totalEvents);
        model.addAttribute("totalContributions", totalContributions);
        model.addAttribute("totalContributionAmount", formattedTotalAmount);
        model.addAttribute("totalMeetings", totalMeetings);

        // Recent lists for dashboard widgets
        model.addAttribute("recentMembers", memberService.getAllMembers().stream().limit(5).toList());
        model.addAttribute("recentEvents", eventService.getAllEvents().stream().limit(5).toList());
        model.addAttribute("recentMeetings", meetingService.getAllMeetings().stream().limit(5).toList());
        model.addAttribute("recentContributions", contributionService.getAllContributions().stream().limit(5).toList());

        return "dashboard/index";
    }
}
