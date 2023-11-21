package com.pymextore.softwarev.service;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.security.NoSuchAlgorithmException;
import java.util.Optional;

import com.pymextore.softwarev.dto.ResponseDto;
import com.pymextore.softwarev.dto.SignInDto;
import com.pymextore.softwarev.dto.SignInReponseDto;
import com.pymextore.softwarev.dto.SignupDto;
import com.pymextore.softwarev.exceptions.AuthenticationFailException;
import com.pymextore.softwarev.exceptions.CustomException;
import com.pymextore.softwarev.model.AuthenticationToken;
import com.pymextore.softwarev.model.User;
import com.pymextore.softwarev.repository.TokenRepository;
import com.pymextore.softwarev.repository.UserRepository;

import org.junit.Before;
import org.junit.Test;

public class UserServiceTest {

    private UserService userService;

    private UserRepository userRepositoryMock;
    private AuthenticationService authenticationServiceMock;

    @Before
    public void setUp() {
        userRepositoryMock = mock(UserRepository.class);
        authenticationServiceMock = mock(AuthenticationService.class);
        userService = new UserService();
        userService.userRepository = userRepositoryMock;
        userService.authenticationService = authenticationServiceMock;
    }

    @Test(expected = CustomException.class)
    public void testSignUpUserAlreadyPresent() {
        SignupDto signupDto = new SignupDto();
        signupDto.setEmail("existing.user@example.com");

        when(userRepositoryMock.findByEmail("existing.user@example.com")).thenReturn(new User());

        userService.signUp(signupDto);
    }

    @Test(expected = CustomException.class)
    public void testSignUpInvalidRole() {
        SignupDto signupDto = new SignupDto();
        signupDto.setRole("invalid_role");

        userService.signUp(signupDto);
    }


    @Test(expected = AuthenticationFailException.class)
    public void testSignInInvalidUser() {
        SignInDto signInDto = new SignInDto();
        signInDto.setEmail("nonexistent.user@example.com");

        when(userRepositoryMock.findByEmail("nonexistent.user@example.com")).thenReturn(null);

        userService.signIn(signInDto);
    }





    @Test(expected = CustomException.class)
    public void testUpdateUserNotFound() {
        int userId = 1;

        when(userRepositoryMock.findById(userId)).thenReturn(Optional.empty());

        userService.updateUser(userId, new SignupDto());
    }

    @Test
    public void testAuthenticate() {
        String token = "validToken";
        User user = new User();
        when(userRepositoryMock.findByToken(token)).thenReturn(user);

        assertEquals(user, userService.authenticate(token));
    }

    @Test(expected = CustomException.class)
    public void testAuthenticateInvalidToken() {
        String invalidToken = "invalidToken";
        when(userRepositoryMock.findByToken(invalidToken)).thenReturn(null);

        userService.authenticate(invalidToken);
    }
}
