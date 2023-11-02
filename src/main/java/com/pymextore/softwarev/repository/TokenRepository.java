package com.pymextore.softwarev.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pymextore.softwarev.model.AuthenticationToken;
import com.pymextore.softwarev.model.User;

@Repository
public interface TokenRepository extends JpaRepository<AuthenticationToken, Integer> {

    AuthenticationToken findByUser(User user);
    AuthenticationToken findByToken(String token);
}