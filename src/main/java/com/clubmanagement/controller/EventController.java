package com.clubmanagement.controller;

import com.clubmanagement.entity.Event;
import com.clubmanagement.service.EventService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/events")
public class EventController {

    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping
    public String listEvents(@RequestParam(value = "keyword", required = false) String keyword, Model model) {
        List<Event> events = eventService.searchEvents(keyword);
        model.addAttribute("events", events);
        model.addAttribute("keyword", keyword);
        return "events/list";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("event", new Event());
        model.addAttribute("isEdit", false);
        return "events/form";
    }

    @PostMapping("/save")
    public String saveEvent(@Valid @ModelAttribute("event") Event event,
                            BindingResult bindingResult,
                            Model model,
                            RedirectAttributes redirectAttributes) {
        if (eventService.isEventCodeDuplicate(event.getEventCode(), event.getId())) {
            bindingResult.rejectValue("eventCode", "error.event", "Mã sự kiện này đã tồn tại");
        }

        if (event.getEndTime() != null && event.getStartTime() != null && event.getEndTime().isBefore(event.getStartTime())) {
            bindingResult.rejectValue("endTime", "error.event", "Thời gian kết thúc phải sau thời gian bắt đầu");
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("isEdit", event.getId() != null);
            return "events/form";
        }

        try {
            if (event.getId() == null) {
                eventService.saveEvent(event);
                redirectAttributes.addFlashAttribute("successMessage", "Thêm mới sự kiện '" + event.getTitle() + "' thành công!");
            } else {
                eventService.updateEvent(event.getId(), event);
                redirectAttributes.addFlashAttribute("successMessage", "Cập nhật sự kiện '" + event.getTitle() + "' thành công!");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Lỗi khi lưu sự kiện: " + e.getMessage());
        }

        return "redirect:/events";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model, RedirectAttributes redirectAttributes) {
        try {
            Event event = eventService.getEventById(id);
            model.addAttribute("event", event);
            model.addAttribute("isEdit", true);
            return "events/form";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/events";
        }
    }

    @GetMapping("/view/{id}")
    public String viewEvent(@PathVariable("id") Long id, Model model, RedirectAttributes redirectAttributes) {
        try {
            Event event = eventService.getEventById(id);
            model.addAttribute("event", event);
            return "events/detail";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/events";
        }
    }

    @GetMapping("/delete/{id}")
    public String deleteEvent(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        try {
            Event event = eventService.getEventById(id);
            eventService.deleteEvent(id);
            redirectAttributes.addFlashAttribute("successMessage", "Xóa sự kiện '" + event.getTitle() + "' thành công!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Không thể xóa sự kiện: " + e.getMessage());
        }
        return "redirect:/events";
    }
}
