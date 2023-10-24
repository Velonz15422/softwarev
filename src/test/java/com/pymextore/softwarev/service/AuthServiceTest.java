package com.pymextore.softwarev.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.pymextore.softwarev.model.AuthToken;
import com.pymextore.softwarev.model.User;
import com.pymextore.softwarev.repository.TokenRepository;

public class AuthServiceTest {

    @InjectMocks
    private AuthService authService;

    @Mock
    private TokenRepository repository;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testSaveConfirmationToken() {
        AuthToken authToken = new AuthToken();
        authService.saveConfirmationToken(authToken);

        verify(repository, times(1)).save(authToken);
    }

    @Test
    public void testGetToken() {
        User user = new User();
        AuthToken authToken = new AuthToken();
        when(repository.findByUser(user)).thenReturn(authToken);

        AuthToken result = authService.getToken(user);

        verify(repository, times(1)).findByUser(user);
        assertEquals(authToken, result);
    }
}