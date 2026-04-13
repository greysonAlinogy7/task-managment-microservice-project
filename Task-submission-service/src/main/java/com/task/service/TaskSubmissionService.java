package com.task.service;

import com.task.dto.TaskDTO;
import com.task.model.Submission;
import com.task.repository.SubmisionRepository;
import com.task.service.client.TaskService;
import com.task.service.client.UserService;
import com.task.service.impl.ISubmissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


@Service
@RequiredArgsConstructor
public class TaskSubmissionService implements ISubmissionService {

    private  final SubmisionRepository submisionRepository;
    private final TaskService taskService;



    @Override
    public Submission submitTask(Long taskId, String gitHubLink, Long userId, String jwt) throws Exception {
        TaskDTO task = taskService.getTaskById(taskId,jwt);
        if (task != null){
            Submission submission = new Submission();
            submission.setTaskId(taskId);
            submission.setUserId(userId);
            submission.setGitHubLink(gitHubLink);
            submission.setSubmittedAt(LocalDateTime.now());
            return submisionRepository.save(submission);
        }
        throw  new Exception("task not found ");
    }

    @Override
    public Submission getTaskSubmissionById(Long submissionId) throws Exception {
        return submisionRepository.findById(submissionId).orElseThrow(() -> new Exception("Task submission not found"));
    }

    @Override
    public List<Submission> getAllSubmittedTasks() {
        return submisionRepository.findAll();
    }

    @Override
    public List<Submission> getTaskSubmissionsByTaskId(Long taskId) {
        return submisionRepository.findByTaskId(taskId);
    }

    @Override
    public Submission acceptDeclineSubmission(Long id, String status) throws Exception {
        Submission submission = getTaskSubmissionById(id);
        submission.setStatus(status);
        if (status.equals("ACCEPT")){
            taskService.completeTask(submission.getTaskId());
        }

        return submisionRepository.save(submission);
    }
}
