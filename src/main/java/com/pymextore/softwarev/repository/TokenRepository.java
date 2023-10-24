package com.pymextore.softwarev.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pymextore.softwarev.model.AuthToken;
import com.pymextore.softwarev.model.User;

public interface TokenRepository extends JpaRepository<AuthToken, Integer>{
    AuthToken findByUser(User user);
}
