package com.task.service;

import com.task.config.JwtProvider;
import com.task.model.User;
import com.task.repository.UserRepository;
import com.task.service.impl.iUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class UserService implements iUserService {

    private  final UserRepository userRepository;

    @Override
    public User getUserByProfile(String jwt) {
        String email = JwtProvider.getEmailFromToken(jwt);
        return userRepository.findByEmail(email);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
