package com.task.service.impl;

import com.task.model.Submission;

import java.util.List;

public interface ISubmissionService {
    Submission submitTask(Long taskId, String gitHubLink, Long userId, String jwt) throws Exception;
    Submission getTaskSubmissionById(Long submissionId) throws Exception;
    List<Submission> getAllSubmittedTasks();
    List<Submission> getTaskSubmissionsByTaskId(Long taskId);
    Submission acceptDeclineSubmission(Long id, String status) throws Exception;
}
