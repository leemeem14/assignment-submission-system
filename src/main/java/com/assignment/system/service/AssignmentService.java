package com.assignment.system.service;

import com.assignment.system.dto.AssignmentDto;
import com.assignment.system.entity.Assignment;
import com.assignment.system.entity.User;
import com.assignment.system.repository.AssignmentRepository;
import com.assignment.system.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AssignmentService {

    @Autowired
    private AssignmentRepository assignmentRepository;

    @Autowired
    private UserRepository userRepository;

    public Assignment createAssignment(AssignmentDto dto, String professorUsername) {
        User professor = userRepository.findByUsername(professorUsername)
                .orElseThrow(() -> new RuntimeException("교수를 찾을 수 없습니다"));

        Assignment assignment = Assignment.builder()
                .title(dto.getTitle())
                .description(dto.getDescription())
                .dueDate(dto.getDueDate())
                .professor(professor)
                .build();

        return assignmentRepository.save(assignment);
    }

    public Assignment updateAssignment(Long id, AssignmentDto dto) {
        Assignment assignment = assignmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("과제를 찾을 수 없습니다"));

        assignment.setTitle(dto.getTitle());
        assignment.setDescription(dto.getDescription());
        assignment.setDueDate(dto.getDueDate());

        return assignmentRepository.save(assignment);
    }

    public void deleteAssignment(Long id) {
        assignmentRepository.deleteById(id);
    }

    public List<Assignment> findAll() {
        return assignmentRepository.findByOrderByCreatedAtDesc();
    }

    public List<Assignment> findByProfessor(String professorUsername) {
        User professor = userRepository.findByUsername(professorUsername)
                .orElseThrow(() -> new RuntimeException("교수를 찾을 수 없습니다"));
        return assignmentRepository.findByProfessor(professor);
    }

    public Optional<Assignment> findById(Long id) {
        return assignmentRepository.findById(id);
    }
}
