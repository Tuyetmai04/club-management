package com.clubmanagement.controller;

import com.clubmanagement.entity.Member;
import com.clubmanagement.service.MemberService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping
    public String listMembers(@RequestParam(value = "keyword", required = false) String keyword,
                              @RequestParam(value = "status", required = false) String status,
                              Model model) {
        List<Member> members = memberService.searchMembers(keyword, status);
        model.addAttribute("members", members);
        model.addAttribute("keyword", keyword);
        model.addAttribute("status", status);
        return "members/list";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("member", new Member());
        model.addAttribute("isEdit", false);
        return "members/form";
    }

    @PostMapping("/save")
    public String saveMember(@Valid @ModelAttribute("member") Member member,
                             BindingResult bindingResult,
                             Model model,
                             RedirectAttributes redirectAttributes) {
        // Validate duplicate member code
        if (memberService.isMemberCodeDuplicate(member.getMemberCode(), member.getId())) {
            bindingResult.rejectValue("memberCode", "error.member", "Mã thành viên này đã tồn tại trong hệ thống");
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("isEdit", member.getId() != null);
            return "members/form";
        }

        try {
            if (member.getId() == null) {
                memberService.saveMember(member);
                redirectAttributes.addFlashAttribute("successMessage", "Thêm mới thành viên '" + member.getFullName() + "' thành công!");
            } else {
                memberService.updateMember(member.getId(), member);
                redirectAttributes.addFlashAttribute("successMessage", "Cập nhật thành viên '" + member.getFullName() + "' thành công!");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Lỗi khi lưu thành viên: " + e.getMessage());
        }

        return "redirect:/members";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model, RedirectAttributes redirectAttributes) {
        try {
            Member member = memberService.getMemberById(id);
            model.addAttribute("member", member);
            model.addAttribute("isEdit", true);
            return "members/form";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/members";
        }
    }

    @GetMapping("/view/{id}")
    public String viewMember(@PathVariable("id") Long id, Model model, RedirectAttributes redirectAttributes) {
        try {
            Member member = memberService.getMemberById(id);
            model.addAttribute("member", member);
            return "members/detail";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/members";
        }
    }

    @GetMapping("/delete/{id}")
    public String deleteMember(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        try {
            Member member = memberService.getMemberById(id);
            memberService.deleteMember(id);
            redirectAttributes.addFlashAttribute("successMessage", "Xóa thành viên '" + member.getFullName() + "' thành công!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Không thể xóa thành viên: " + e.getMessage());
        }
        return "redirect:/members";
    }
}
