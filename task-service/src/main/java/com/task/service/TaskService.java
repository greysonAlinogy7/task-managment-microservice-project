package com.task.service;

import com.task.domain.TaskStatus;
import com.task.model.Task;
import com.task.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskService implements ITaskService{

    private  final TaskRepository taskRepository;

    @Override
    public Task createTask(Task task, String requesterRole) throws Exception {
        if (requesterRole.equals("ROLE_ADMIN")){
            throw  new Exception("only admin can create task");

        }

        task.setStatus(TaskStatus.PENDING);
        task.setCreatedAt(LocalDateTime.now());
        return taskRepository.save(task);
    }

    @Override
    public Task getTaskById(Long id) throws Exception {
        return taskRepository.findById(id).orElseThrow(() -> new Exception("task not found"));
    }

    @Override
    public List<Task> getAllTask(TaskStatus status) {
        List<Task> allTask = taskRepository.findAll();
        List<Task> filteredTask = allTask.stream().filter(task -> status==null || task.getStatus().name().equalsIgnoreCase(status.toString())).collect(Collectors.toList());
        return filteredTask;
    }

    @Override
    public Task updateTask(Long id, Task updatedTask, Long UserId) throws Exception {
        Task existingtask = getTaskById(id);
        if (updatedTask.getTitle() != null){
            existingtask.setTitle(updatedTask.getTitle());
        }

        if (updatedTask.getImage() != null){
            existingtask.setImage(updatedTask.getImage());
        }

        if (updatedTask.getDescription() != null){
            existingtask.setDescription(updatedTask.getDescription());
        }

        if (updatedTask.getStatus() != null){
            existingtask.setStatus(updatedTask.getStatus());
        }

        if (updatedTask.getDeadline() != null){
            existingtask.setDeadline(updatedTask.getDeadline());
        }
        return taskRepository.save(existingtask);
    }

    @Override
    public void deleteTask(Long id) throws Exception {
        getTaskById(id);
        taskRepository.deleteById(id);

    }

    @Override
    public Task assignToUser(Long userId, Long taskId) throws Exception {
        Task task = getTaskById(taskId);
        task.setAssignedUserId(userId);
        task.setStatus(TaskStatus.DONE);
        return taskRepository.save(task);
    }

    @Override
    public List<Task> assignedUsersTask(Long userId, TaskStatus status) {
        List<Task> allTask =taskRepository.findByAssignedUserId(userId);
        List<Task> filteredTask = allTask.stream().filter(task -> status == null || task.getStatus().name().equalsIgnoreCase(status.toString())).collect(Collectors.toList());
        return filteredTask;
    }

    @Override
    public Task completeTask(Long taskId) throws Exception {
        Task task = getTaskById(taskId);
        task.setStatus(TaskStatus.DONE);
        return taskRepository.save(task);
    }
}
