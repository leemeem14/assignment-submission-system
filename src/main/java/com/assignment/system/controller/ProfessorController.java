package com.assignment.system.controller;

import com.assignment.system.dto.AssignmentDto;
import com.assignment.system.dto.CommentDto;
import com.assignment.system.dto.GradeDto;
import com.assignment.system.entity.Assignment;
import com.assignment.system.entity.Submission;
import com.assignment.system.service.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/professor")
public class ProfessorController {

    @Autowired
    private AssignmentService assignmentService;

    @Autowired
    private SubmissionService submissionService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private GradeService gradeService;

    @GetMapping("/dashboard")
    public String dashboard(Authentication authentication, Model model) {
        List<Assignment> assignments = assignmentService.findByProfessor(authentication.getName());
        model.addAttribute("assignments", assignments);
        return "professor/dashboard";
    }

    @GetMapping("/assignments/new")
    public String newAssignmentForm(Model model) {
        model.addAttribute("assignment", new AssignmentDto());
        return "professor/assignment-form";
    }

    @PostMapping("/assignments")
    public String createAssignment(@Valid @ModelAttribute("assignment") AssignmentDto dto,
                                 BindingResult result, Authentication authentication) {
        if (result.hasErrors()) {
            return "professor/assignment-form";
        }

        assignmentService.createAssignment(dto, authentication.getName());
        return "redirect:/professor/dashboard";
    }

    @GetMapping("/assignments/{id}/edit")
    public String editAssignmentForm(@PathVariable Long id, Model model) {
        Assignment assignment = assignmentService.findById(id)
                .orElseThrow(() -> new RuntimeException("과제를 찾을 수 없습니다"));

        AssignmentDto dto = new AssignmentDto();
        dto.setId(assignment.getId());
        dto.setTitle(assignment.getTitle());
        dto.setDescription(assignment.getDescription());
        dto.setDueDate(assignment.getDueDate());

        model.addAttribute("assignment", dto);
        return "professor/assignment-form";
    }

    @PostMapping("/assignments/{id}")
    public String updateAssignment(@PathVariable Long id,
                                 @Valid @ModelAttribute("assignment") AssignmentDto dto,
                                 BindingResult result) {
        if (result.hasErrors()) {
            return "professor/assignment-form";
        }

        assignmentService.updateAssignment(id, dto);
        return "redirect:/professor/dashboard";
    }

    @PostMapping("/assignments/{id}/delete")
    public String deleteAssignment(@PathVariable Long id) {
        assignmentService.deleteAssignment(id);
        return "redirect:/professor/dashboard";
    }

    @GetMapping("/assignments/{id}/submissions")
    public String viewSubmissions(@PathVariable Long id, Model model) {
        Assignment assignment = assignmentService.findById(id)
                .orElseThrow(() -> new RuntimeException("과제를 찾을 수 없습니다"));
        List<Submission> submissions = submissionService.findByAssignment(id);

        model.addAttribute("assignment", assignment);
        model.addAttribute("submissions", submissions);
        return "professor/submissions";
    }

    // ProfessorController.java 내 상세 보기 핸들러
    @GetMapping("/submissions/{id}")
    public String viewSubmissionDetail(@PathVariable Long id, Model model) {
        Submission submission = submissionService.findById(id)
                .orElseThrow(() -> new RuntimeException("제출물을 찾을 수 없습니다"));
        model.addAttribute("submission", submission);
        model.addAttribute("comments", commentService.findBySubmission(id));
        model.addAttribute("newComment", new CommentDto());

        // 핵심 수정: Optional<Grade> -> GradeDto로 매핑
        GradeDto gradeDto = gradeService.findBySubmission(id)
                .map(g -> {
                    GradeDto dto = new GradeDto();
                    dto.setId(g.getId());
                    dto.setScore(g.getScore());
                    dto.setFeedback(g.getFeedback());
                    dto.setGradedAt(g.getGradedAt());
                    return dto;
                })
                // 필요 시 orElseGet으로 지연 생성
                .orElseGet(GradeDto::new);

        model.addAttribute("grade", gradeDto);
        return "professor/submission-detail";
    }

    @PostMapping("/submissions/{id}/grade")
    public String gradeSubmission(@PathVariable Long id,
                                @Valid @ModelAttribute("grade") GradeDto dto,
                                BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "redirect:/professor/submissions/" + id;
        }

        gradeService.gradeSubmission(id, dto);
        return "redirect:/professor/submissions/" + id;
    }

    @PostMapping("/submissions/{id}/comments")
    public String addComment(@PathVariable Long id,
                           @Valid @ModelAttribute("newComment") CommentDto dto,
                           BindingResult result, Authentication authentication) {
        if (result.hasErrors()) {
            return "redirect:/professor/submissions/" + id;
        }

        commentService.addComment(id, dto, authentication.getName());
        return "redirect:/professor/submissions/" + id;
    }
}
