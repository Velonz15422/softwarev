package com.pymextore.softwarev.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pymextore.softwarev.dto.ResponseDto;
import com.pymextore.softwarev.dto.SignInDto;
import com.pymextore.softwarev.dto.SignInReponseDto;
import com.pymextore.softwarev.dto.SignupDto;
import com.pymextore.softwarev.service.UserService;
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("user")
@RestController
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/signup")
    public ResponseDto signup(@RequestBody SignupDto signupDto) {
        return userService.signUp(signupDto);
    }


    @PostMapping("/signin")
    public SignInReponseDto signIn(@RequestBody SignInDto signInDto) {
        return userService.signIn(signInDto);
    }

    @PutMapping("/update/{userId}")
    public ResponseDto updateUser(@PathVariable Integer userId, @RequestBody SignupDto updatedUserData) {
        return userService.updateUser(userId, updatedUserData);
    }


}
