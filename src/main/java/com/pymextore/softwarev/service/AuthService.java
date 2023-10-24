package com.pymextore.softwarev.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pymextore.softwarev.model.AuthToken;
import com.pymextore.softwarev.model.User;
import com.pymextore.softwarev.repository.TokenRepository;

@Service
public class AuthService {

    @Autowired
    TokenRepository repository;

    public void saveConfirmationToken(AuthToken authenticationToken) {
        repository.save(authenticationToken);
    }

    public AuthToken getToken(User user) {
        return repository.findByUser(user);
    }

    
    
}
