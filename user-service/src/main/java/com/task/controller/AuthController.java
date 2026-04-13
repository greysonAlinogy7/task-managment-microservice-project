package com.task.controller;

import com.task.config.JwtProvider;
import com.task.model.User;
import com.task.repository.UserRepository;
import com.task.request.LoginRequest;
import com.task.response.AuthResponse;
import com.task.service.CustomUserServiceImplementation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private  final UserRepository userRepository;
    private  final PasswordEncoder passwordEncoder;
    private final CustomUserServiceImplementation customUserDetail;


    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> createUserHandler(@RequestBody User user) throws Exception {
        String email = user.getEmail();
        String password = user.getPassword();
        String fullName = user.getFullName();
        String role=user.getRole();
//        User isEmailExist = userRepository.findByEmail(email);
//        if (isEmailExist!=null){
//            throw  new Exception("user already exist");
//        }
        User createUser = new User();
        createUser.setEmail(email);
        createUser.setFullName(fullName);
        createUser.setRole(role);
        createUser.setPassword(passwordEncoder.encode(password));

        User savedUser = userRepository.save(createUser);

        Authentication authentication = new UsernamePasswordAuthenticationToken(savedUser.getEmail(), password);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = JwtProvider.generateToken(authentication);

        AuthResponse authResponse = new AuthResponse();
        authResponse.setJwt(token);
        authResponse.setMessage("Registered successfully");
        authResponse.setStatus(true);
        return  new ResponseEntity<>(authResponse, HttpStatus.OK);

    }


    @PostMapping("/login")
    public ResponseEntity<AuthResponse> signIn(@RequestBody LoginRequest loginRequest) throws Exception {
        String username = loginRequest.getEmail();
        String password = loginRequest.getPassword();

        Authentication authentication = authenticate(username, password);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = JwtProvider.generateToken(authentication);
        AuthResponse authResponse = new AuthResponse();
        authResponse.setMessage("login success");
        authResponse.setJwt(token);
        authResponse.setStatus(true);


        return  ResponseEntity.ok(authResponse);
    }

    private Authentication authenticate(String username, String password) throws Exception {
        UserDetails userDetails = customUserDetail.loadUserByUsername(username);
        if (userDetails == null){
            throw new BadCredentialsException("Invalid username or password");
        }

        if (!passwordEncoder.matches(password, userDetails.getPassword())){
            throw  new BadCredentialsException("invalid username or password");
        }

        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

    }

}
