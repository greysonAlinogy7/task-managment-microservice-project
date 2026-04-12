package com.task.service;

import com.task.domain.TaskStatus;
import com.task.model.Task;

import java.util.List;

public interface ITaskService {
    Task createTask(Task task, String requesterRole) throws Exception;
    Task getTaskById(Long id) throws Exception;
    List<Task> getAllTask(TaskStatus status);
    Task updateTask(Long id, Task updatedTask, Long UserId) throws Exception;
    void deleteTask(Long id) throws Exception;
    Task assignToUser(Long userId, Long taskId) throws Exception;
    List<Task> assignedUsersTask(Long userId, TaskStatus status);
    Task completeTask(Long taskId) throws Exception;
}
