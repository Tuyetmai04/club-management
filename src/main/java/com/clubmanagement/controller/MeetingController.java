package com.clubmanagement.controller;

import com.clubmanagement.entity.Meeting;
import com.clubmanagement.service.MeetingService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/meetings")
public class MeetingController {

    private final MeetingService meetingService;

    public MeetingController(MeetingService meetingService) {
        this.meetingService = meetingService;
    }

    @GetMapping
    public String listMeetings(@RequestParam(value = "keyword", required = false) String keyword, Model model) {
        List<Meeting> meetings = meetingService.searchMeetings(keyword);
        model.addAttribute("meetings", meetings);
        model.addAttribute("keyword", keyword);
        return "meetings/list";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("meeting", new Meeting());
        model.addAttribute("isEdit", false);
        return "meetings/form";
    }

    @PostMapping("/save")
    public String saveMeeting(@Valid @ModelAttribute("meeting") Meeting meeting,
                              BindingResult bindingResult,
                              Model model,
                              RedirectAttributes redirectAttributes) {
        if (meetingService.isMeetingCodeDuplicate(meeting.getMeetingCode(), meeting.getId())) {
            bindingResult.rejectValue("meetingCode", "error.meeting", "Mã cuộc họp này đã tồn tại");
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("isEdit", meeting.getId() != null);
            return "meetings/form";
        }

        try {
            if (meeting.getId() == null) {
                meetingService.saveMeeting(meeting);
                redirectAttributes.addFlashAttribute("successMessage", "Thêm mới cuộc họp '" + meeting.getTitle() + "' thành công!");
            } else {
                meetingService.updateMeeting(meeting.getId(), meeting);
                redirectAttributes.addFlashAttribute("successMessage", "Cập nhật cuộc họp '" + meeting.getTitle() + "' thành công!");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Lỗi khi lưu cuộc họp: " + e.getMessage());
        }

        return "redirect:/meetings";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model, RedirectAttributes redirectAttributes) {
        try {
            Meeting meeting = meetingService.getMeetingById(id);
            model.addAttribute("meeting", meeting);
            model.addAttribute("isEdit", true);
            return "meetings/form";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/meetings";
        }
    }

    @GetMapping("/view/{id}")
    public String viewMeeting(@PathVariable("id") Long id, Model model, RedirectAttributes redirectAttributes) {
        try {
            Meeting meeting = meetingService.getMeetingById(id);
            model.addAttribute("meeting", meeting);
            return "meetings/detail";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/meetings";
        }
    }

    @GetMapping("/delete/{id}")
    public String deleteMeeting(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        try {
            Meeting meeting = meetingService.getMeetingById(id);
            meetingService.deleteMeeting(id);
            redirectAttributes.addFlashAttribute("successMessage", "Xóa cuộc họp '" + meeting.getTitle() + "' thành công!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Không thể xóa cuộc họp: " + e.getMessage());
        }
        return "redirect:/meetings";
    }
}
