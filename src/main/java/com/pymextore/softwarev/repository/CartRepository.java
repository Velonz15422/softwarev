package com.pymextore.softwarev.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pymextore.softwarev.model.Cart;
import com.pymextore.softwarev.model.User;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Integer> {

    List<Cart> findAllByUserOrderByCreatedDateDesc(User user);
}
