package com.assignment.system.repository;

import com.assignment.system.entity.Assignment;
import com.assignment.system.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AssignmentRepository extends JpaRepository<Assignment, Long> {
    List<Assignment> findByProfessor(User professor);
    List<Assignment> findByOrderByCreatedAtDesc();
}
