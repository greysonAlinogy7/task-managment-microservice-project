package com.task.service.impl;

import com.task.model.User;

import java.util.List;

public interface iUserService {
     User getUserByProfile(String jwt);
     List<User> getAllUsers();
}
