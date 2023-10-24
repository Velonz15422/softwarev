package com.pymextore.softwarev.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pymextore.softwarev.dto.ResponseDto;
import com.pymextore.softwarev.dto.SignInDto;
import com.pymextore.softwarev.dto.SignInResponseDto;
import com.pymextore.softwarev.dto.SignupDto;
import com.pymextore.softwarev.exceptions.CustomException;
import com.pymextore.softwarev.repository.UserRepository;
import com.pymextore.softwarev.service.AuthService;
import com.pymextore.softwarev.service.UserService;

@RequestMapping("/user")
@RestController
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    AuthService authService;

    /*@GetMapping("/all")
    public List<User> findAllUser(@RequestParam("token") String token) throws AuthenticationFailException {
        authService.authenticate(token);
        return userRepository.findAll();
    }*/

    @PostMapping("/signup")
    public ResponseDto Signup(@RequestBody SignupDto signupDto) throws CustomException {
        return userService.signUp(signupDto);
    }

  @PostMapping("/signIn")
    public SignInResponseDto Signup(@RequestBody SignInDto signInDto) throws CustomException {
        return userService.signIn(signInDto);
    }

    // Cierre sesion


}
