package com.pymextore.softwarev.service;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;





import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.pymextore.softwarev.dto.ResponseDto;
import com.pymextore.softwarev.dto.SignInDto;
import com.pymextore.softwarev.dto.SignInResponseDto;
import com.pymextore.softwarev.dto.SignupDto;
import com.pymextore.softwarev.model.AuthToken;
import com.pymextore.softwarev.model.User;
import com.pymextore.softwarev.repository.UserRepository;

import jakarta.xml.bind.DatatypeConverter;

public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private AuthService authService;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testSignUp() throws NoSuchAlgorithmException {
        SignupDto signupDto = new SignupDto();
        signupDto.setFirstName("John");
        signupDto.setLastName("Doe");
        signupDto.setEmail("johndoe@example.com");
        signupDto.setPassword("password123");

        User existingUser = new User();
        when(userRepository.findByEmail(signupDto.getEmail())).thenReturn(existingUser);

        ResponseDto response = userService.signUp(signupDto);

        verify(userRepository, times(1)).findByEmail(signupDto.getEmail());
        verify(userRepository, times(1)).save(any(User.class));
        verify(authService, times(1)).saveConfirmationToken(any(AuthToken.class));

        assertEquals("Success", response.getStatus());
    }

    @Test
    public void testSignIn() throws NoSuchAlgorithmException {
        SignInDto signInDto = new SignInDto();
        signInDto.setEmail("johndoe@example.com");
        signInDto.setPassword("password123");

        User user = new User();
        user.setEmail(signInDto.getEmail());
        user.setPasswoprd(DatatypeConverter.printHexBinary(MessageDigest.getInstance("MD5").digest(signInDto.getPassword().getBytes())).toUpperCase());

        when(userRepository.findByEmail(signInDto.getEmail())).thenReturn(user);
        when(authService.getToken(user)).thenReturn(new AuthToken(user));

        SignInResponseDto response = userService.signIn(signInDto);

        verify(userRepository, times(1)).findByEmail(signInDto.getEmail());
        verify(authService, times(1)).getToken(user);

        assertEquals("success", response.getStatus());
    }
}

