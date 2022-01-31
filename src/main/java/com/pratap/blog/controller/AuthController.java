package com.pratap.blog.controller;

import com.pratap.blog.entity.Role;
import com.pratap.blog.entity.User;
import com.pratap.blog.exception.ResourceNotFoundException;
import com.pratap.blog.model.response.JWTAuthResponse;
import com.pratap.blog.model.request.SignInRequestModel;
import com.pratap.blog.model.request.SignUpRequestModel;
import com.pratap.blog.repository.RoleRepository;
import com.pratap.blog.repository.UserRepository;
import com.pratap.blog.security.JwtTokenProvider;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Collections;

@Api(value = "Auth controller exposes sign-in and sign-up REST APIs")
@Slf4j
@RestController
@RequestMapping("/blog-api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @ApiOperation(value = "REST API to Signin or Login user to Blog app")
    @PostMapping("/sign-in")
    public ResponseEntity<JWTAuthResponse> authenticateUser(@Valid @RequestBody SignInRequestModel signInRequestModel){

        log.info("Executing authenticateUser() with usernameOrEmail={}", signInRequestModel.getUsernameOrEmail());
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                signInRequestModel.getUsernameOrEmail(),
                signInRequestModel.getPassword()
        ));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwtToken = jwtTokenProvider.generateJwtToken(authentication);
        return new ResponseEntity<>(new JWTAuthResponse(jwtToken), HttpStatus.OK);
    }

    @ApiOperation(value = "REST API to Register or Signup user to Blog app")
    @PostMapping("/sign-up")
    public ResponseEntity<String> registerUser(@Valid @RequestBody SignUpRequestModel signUpRequestModel){

        log.info("Executing registerUser()...");
        // add check for username exists in DB
        if (userRepository.existsByUsername(signUpRequestModel.getUsername()))
            return new ResponseEntity<>("username is already taken : "
                    +signUpRequestModel.getUsername(), HttpStatus.BAD_REQUEST);
        // add check for email exists in DB
        if (userRepository.existsByEmail(signUpRequestModel.getEmail()))
            return new ResponseEntity<>("email is already taken : "
                    +signUpRequestModel.getEmail(), HttpStatus.BAD_REQUEST);

        signUpRequestModel.setPassword(passwordEncoder.encode(signUpRequestModel.getPassword()));

        User user = modelMapper.map(signUpRequestModel, User.class);
        if (signUpRequestModel.getRole().equalsIgnoreCase("admin")) {
            Role role = roleRepository.findByName("ROLE_ADMIN")
                    .orElseThrow(() -> new ResourceNotFoundException("First create the entry in Role(ADMIN/USER) in DB"));
            user.setRoles(Collections.singleton(role));
        }

        if (signUpRequestModel.getRole().equalsIgnoreCase("user")) {
            Role role = roleRepository.findByName("ROLE_USER")
                    .orElseThrow(() -> new ResourceNotFoundException("First create the entry in Role(ADMIN/USER) in DB"));
            user.setRoles(Collections.singleton(role));
        }
        userRepository.save(user);
        return new ResponseEntity<>("User registered successfully", HttpStatus.OK);
    }
}
