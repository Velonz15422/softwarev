package com.pymextore.softwarev.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pymextore.softwarev.dto.ResponseDto;
import com.pymextore.softwarev.dto.SignInDto;
import com.pymextore.softwarev.dto.SignInResponseDto;
import com.pymextore.softwarev.dto.SignupDto;
import com.pymextore.softwarev.exceptions.AuthFailException;
import com.pymextore.softwarev.exceptions.CustomException;
import com.pymextore.softwarev.model.AuthToken;
import com.pymextore.softwarev.model.User;
import com.pymextore.softwarev.repository.UserRepository;

import jakarta.transaction.Transactional;
import jakarta.xml.bind.DatatypeConverter;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    AuthService authService;

    @Transactional
    public ResponseDto signUp(SignupDto signUpDto){
        
        if (Objects.nonNull(userRepository.findByEmail(signUpDto.getEmail()))) {
            throw new CustomException("User already logged");
        }
        String encryptedPassword = signUpDto.getPassword();
        try {
            encryptedPassword = hashPassword(signUpDto.getPassword());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
 
        User user = new User(signUpDto.getFirstName(), signUpDto.getLastName(),
         signUpDto.getEmail(), encryptedPassword);

         userRepository.save(user);

        final AuthToken authToken = new AuthToken(user);

        authService.saveConfirmationToken(authToken);

        ResponseDto responseDto = new ResponseDto("Success", "response");
        return responseDto;
    }


    String hashPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(password.getBytes());
        byte[] digest = md.digest();
        String myHash = DatatypeConverter
                .printHexBinary(digest).toUpperCase();
        return myHash;
    }


    public SignInResponseDto signIn(SignInDto signInDto) {
        User user = userRepository.findByEmail(signInDto.getEmail());

        if (Objects.isNull(user)) {
            throw new AuthFailException("User isnt valid");
        }
            try {
                if(user.getPasswoprd().equals(hashPassword(signInDto.getPassword()))){
                                throw new AuthFailException("wrong password");

                }
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
            AuthToken token = authService.getToken(user);

            if (Objects.isNull(token)) {
                throw new CustomException("Token isnt present");
            }
            return new SignInResponseDto("success", token.getToken());
    }
}
