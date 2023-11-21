package com.pymextore.softwarev.service;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.*;

import com.pymextore.softwarev.exceptions.AuthenticationFailException;
import com.pymextore.softwarev.model.AuthenticationToken;
import com.pymextore.softwarev.model.User;
import com.pymextore.softwarev.repository.TokenRepository;

import org.junit.Before;
import org.junit.Test;

public class AuthenticationServiceTest {

    private AuthenticationService authenticationService;

    private TokenRepository tokenRepositoryMock;

    @Before
    public void setUp() {
        tokenRepositoryMock = mock(TokenRepository.class);
        authenticationService = new AuthenticationService();
        authenticationService.tokenRepository = tokenRepositoryMock;
    }

    @Test
    public void testSaveConfirmationToken() {
        AuthenticationToken authenticationToken = new AuthenticationToken();
        authenticationService.saveConfirmationToken(authenticationToken);

        verify(tokenRepositoryMock, times(1)).save(authenticationToken);
    }

    @Test
    public void testGetToken() {
        User user = new User();
        when(tokenRepositoryMock.findByUser(user)).thenReturn(new AuthenticationToken());

        AuthenticationToken resultToken = authenticationService.getToken(user);

        assertNotNull(resultToken);
    }

    @Test
    public void testGetUserValidToken() {
        String token = "validToken";
        AuthenticationToken authenticationToken = new AuthenticationToken();
        authenticationToken.setUser(new User());
        when(tokenRepositoryMock.findByToken(token)).thenReturn(authenticationToken);

        User resultUser = authenticationService.getUser(token);

        assertNotNull(resultUser);
    }

    @Test
    public void testGetUserInvalidToken() {
        String token = "invalidToken";
        when(tokenRepositoryMock.findByToken(token)).thenReturn(null);

        User resultUser = authenticationService.getUser(token);

        assertNull(resultUser);
    }


    @Test(expected = AuthenticationFailException.class)
    public void testAuthenticateNullToken() throws AuthenticationFailException {
        authenticationService.authenticate(null);
    }

    @Test(expected = AuthenticationFailException.class)
    public void testAuthenticateInvalidToken() throws AuthenticationFailException {
        String invalidToken = "invalidToken";
        when(tokenRepositoryMock.findByToken(invalidToken)).thenReturn(null);

        authenticationService.authenticate(invalidToken);
    }
}
