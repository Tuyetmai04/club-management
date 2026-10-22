package com.clubmanagement.controller;

import com.clubmanagement.entity.Contribution;
import com.clubmanagement.service.ContributionService;
import com.clubmanagement.service.MemberService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/contributions")
public class ContributionController {

    private final ContributionService contributionService;
    private final MemberService memberService;

    public ContributionController(ContributionService contributionService, MemberService memberService) {
        this.contributionService = contributionService;
        this.memberService = memberService;
    }

    @GetMapping
    public String listContributions(@RequestParam(value = "keyword", required = false) String keyword, Model model) {
        List<Contribution> contributions = contributionService.searchContributions(keyword);
        model.addAttribute("contributions", contributions);
        model.addAttribute("totalAmount", contributionService.getTotalContributionAmount());
        model.addAttribute("keyword", keyword);
        return "contributions/list";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("contribution", new Contribution());
        model.addAttribute("members", memberService.getAllMembers());
        model.addAttribute("isEdit", false);
        return "contributions/form";
    }

    @PostMapping("/save")
    public String saveContribution(@Valid @ModelAttribute("contribution") Contribution contribution,
                                   BindingResult bindingResult,
                                   Model model,
                                   RedirectAttributes redirectAttributes) {
        if (contributionService.isContributionCodeDuplicate(contribution.getContributionCode(), contribution.getId())) {
            bindingResult.rejectValue("contributionCode", "error.contribution", "Mã đóng góp này đã tồn tại");
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("members", memberService.getAllMembers());
            model.addAttribute("isEdit", contribution.getId() != null);
            return "contributions/form";
        }

        try {
            if (contribution.getId() == null) {
                contributionService.saveContribution(contribution);
                redirectAttributes.addFlashAttribute("successMessage", "Thêm mới khoản đóng góp thành công!");
            } else {
                contributionService.updateContribution(contribution.getId(), contribution);
                redirectAttributes.addFlashAttribute("successMessage", "Cập nhật khoản đóng góp thành công!");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Lỗi khi lưu khoản đóng góp: " + e.getMessage());
        }

        return "redirect:/contributions";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model, RedirectAttributes redirectAttributes) {
        try {
            Contribution contribution = contributionService.getContributionById(id);
            model.addAttribute("contribution", contribution);
            model.addAttribute("members", memberService.getAllMembers());
            model.addAttribute("isEdit", true);
            return "contributions/form";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/contributions";
        }
    }

    @GetMapping("/view/{id}")
    public String viewContribution(@PathVariable("id") Long id, Model model, RedirectAttributes redirectAttributes) {
        try {
            Contribution contribution = contributionService.getContributionById(id);
            model.addAttribute("contribution", contribution);
            return "contributions/detail";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/contributions";
        }
    }

    @GetMapping("/delete/{id}")
    public String deleteContribution(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        try {
            Contribution contribution = contributionService.getContributionById(id);
            contributionService.deleteContribution(id);
            redirectAttributes.addFlashAttribute("successMessage", "Xóa khoản đóng góp '" + contribution.getContributionCode() + "' thành công!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Không thể xóa khoản đóng góp: " + e.getMessage());
        }
        return "redirect:/contributions";
    }
}
