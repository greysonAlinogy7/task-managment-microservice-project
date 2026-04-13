package com.task.repository;

import com.task.model.Submission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface SubmisionRepository extends JpaRepository<Submission, Long> {
    List<Submission> findByTaskId(Long taskId);
}
