package com.task.controller;


import com.task.dto.UserDTO;
import com.task.model.Submission;
import com.task.service.TaskSubmissionService;
import com.task.service.client.TaskService;
import com.task.service.client.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class SubmissionController {
    private  final TaskSubmissionService taskSubmissionService;
    private  final UserService userService;
    private  final TaskService taskService;


    @PostMapping
    public ResponseEntity<Submission> submitTask(@RequestParam Long taskId, @RequestParam String github_link, @RequestHeader("Authorization") String jwt) throws Exception {
        UserDTO user = userService.getUserProfile(jwt);
        Submission submission = taskSubmissionService.submitTask(taskId, github_link,user.getId(), jwt);
         return  new ResponseEntity<>(submission, HttpStatus.CREATED);

    }

    @GetMapping("/{id}")
    public ResponseEntity<Submission> getSubmissionById(@PathVariable Long id, @RequestHeader("Authorization") String jwt) throws Exception {
        UserDTO user = userService.getUserProfile(jwt);
        Submission submission = taskSubmissionService.getTaskSubmissionById(id);
        return  new ResponseEntity<>(submission, HttpStatus.CREATED);

    }

    @GetMapping()
    public ResponseEntity<List<Submission>> getAllSubmissions( @RequestHeader("Authorization") String jwt) throws Exception {
        UserDTO user = userService.getUserProfile(jwt);
        List<Submission> submission = taskSubmissionService.getAllSubmittedTasks();
        return  new ResponseEntity<>(submission, HttpStatus.CREATED);

    }

    @GetMapping("/task/{taskId}")
    public ResponseEntity<List<Submission>> getSubmissions(@PathVariable Long taskId, @RequestHeader("Authorization") String jwt) throws Exception {
        UserDTO user = userService.getUserProfile(jwt);
        List<Submission> submission = taskSubmissionService.getTaskSubmissionsByTaskId(taskId);
        return  new ResponseEntity<>(submission, HttpStatus.CREATED);

    }

    @PutMapping("/{id}")
    public ResponseEntity<Submission> acceptOrDeclineSubmission(@PathVariable Long id, @RequestParam String status, @RequestHeader("Authorization") String jwt) throws Exception {
        UserDTO user = userService.getUserProfile(jwt);
        Submission submission = taskSubmissionService.acceptDeclineSubmission(id,status);
        return  new ResponseEntity<>(submission, HttpStatus.CREATED);

    }
}
