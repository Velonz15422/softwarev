package com.pymextore.softwarev.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pymextore.softwarev.dto.ResponseDto;
import com.pymextore.softwarev.dto.SignInDto;
import com.pymextore.softwarev.dto.SignInReponseDto;
import com.pymextore.softwarev.dto.SignupDto;
import com.pymextore.softwarev.exceptions.AuthenticationFailException;
import com.pymextore.softwarev.exceptions.CustomException;
import com.pymextore.softwarev.model.AuthenticationToken;
import com.pymextore.softwarev.model.User;
import com.pymextore.softwarev.repository.UserRepository;

import jakarta.transaction.Transactional;
import jakarta.xml.bind.DatatypeConverter;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    AuthenticationService authenticationService;

    @Transactional
    public ResponseDto signUp(SignupDto signupDto) {
        if (Objects.nonNull(userRepository.findByEmail(signupDto.getEmail()))) {
            throw new CustomException("user already present");
        }

        String role = signupDto.getRole().toLowerCase();

        if (!role.equals("admin") && !role.equals("user")) {
            throw new CustomException("Invalid role. Role must be either 'admin' or 'user'");
        }

        String encryptedpassword = signupDto.getPassword();

        try {
            encryptedpassword = hashPassword(signupDto.getPassword());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        User user = new User(signupDto.getFirstName(), signupDto.getLastName(),
                signupDto.getEmail(), encryptedpassword, signupDto.getAddress(), signupDto.getRole());

        userRepository.save(user);

        final AuthenticationToken authenticationToken = new AuthenticationToken(user);

        authenticationService.saveConfirmationToken(authenticationToken);

        ResponseDto responseDto = new ResponseDto("success", "user created succesfully");
        return responseDto;
    }

    private String hashPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(password.getBytes());
        byte[] digest = md.digest();
        String hash = DatatypeConverter
                .printHexBinary(digest).toUpperCase();
        return hash;
    }

    public SignInReponseDto signIn(SignInDto signInDto) {

        User user = userRepository.findByEmail(signInDto.getEmail());

        if (Objects.isNull(user)) {
            throw new AuthenticationFailException("user is not valid");
        }

        try {
            if (!user.getPasswoprd().equals(hashPassword(signInDto.getPassword()))) {
                throw new AuthenticationFailException("wrong password");
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        AuthenticationToken token = authenticationService.getToken(user);

        if (Objects.isNull(token)) {
            throw new CustomException("token is not present");
        }

        return new SignInReponseDto("sucess", token.getToken());

    }

    @Transactional
    public ResponseDto updateUser(Integer userId, SignupDto updatedUserData) {
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException("User not found"));

        existingUser.setFirstName(updatedUserData.getFirstName());
        existingUser.setLastName(updatedUserData.getLastName());
        existingUser.setPasswoprd(updatedUserData.getPassword());
        existingUser.setAddress(updatedUserData.getAddress());

        userRepository.save(existingUser);

        ResponseDto responseDto = new ResponseDto("success", "User updated successfully");
        return responseDto;
    }

}