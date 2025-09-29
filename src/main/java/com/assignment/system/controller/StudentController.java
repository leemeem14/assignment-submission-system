package com.assignment.system.controller;

import com.assignment.system.dto.CommentDto;
import com.assignment.system.dto.SubmissionDto;
import com.assignment.system.entity.Assignment;
import com.assignment.system.entity.Submission;
import com.assignment.system.service.AssignmentService;
import com.assignment.system.service.CommentService;
import com.assignment.system.service.SubmissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private AssignmentService assignmentService;

    @Autowired
    private SubmissionService submissionService;

    @Autowired
    private CommentService commentService;

    @GetMapping("/dashboard")
    public String dashboard(Authentication authentication, Model model) {
        List<Assignment> assignments = assignmentService.findAll();
        List<Submission> mySubmissions = submissionService.findByStudent(authentication.getName());

        model.addAttribute("assignments", assignments);
        model.addAttribute("submissions", mySubmissions);
        return "student/dashboard";
    }

    @GetMapping("/assignments/{id}")
    public String viewAssignment(@PathVariable Long id, Model model) {
        Assignment assignment = assignmentService.findById(id)
                .orElseThrow(() -> new RuntimeException("과제를 찾을 수 없습니다"));

        model.addAttribute("assignment", assignment);
        model.addAttribute("submission", new SubmissionDto());
        return "student/assignment-detail";
    }

    @PostMapping("/assignments/{id}/submit")
    public String submitAssignment(@PathVariable Long id,
                                 @ModelAttribute("submission") SubmissionDto dto,
                                 Authentication authentication) throws IOException {
        submissionService.submitAssignment(id, dto, authentication.getName());
        return "redirect:/student/dashboard";
    }

    @GetMapping("/submissions/{id}")
    public String viewSubmission(@PathVariable Long id, Model model) {
        Submission submission = submissionService.findById(id)
                .orElseThrow(() -> new RuntimeException("제출물을 찾을 수 없습니다"));

        model.addAttribute("submission", submission);
        model.addAttribute("comments", commentService.findBySubmission(id));
        model.addAttribute("newComment", new CommentDto());
        return "student/submission-detail";
    }

    @PostMapping("/submissions/{id}/comments")
    public String addComment(@PathVariable Long id,
                           @ModelAttribute("newComment") CommentDto dto,
                           Authentication authentication) {
        commentService.addComment(id, dto, authentication.getName());
        return "redirect:/student/submissions/" + id;
    }
}
