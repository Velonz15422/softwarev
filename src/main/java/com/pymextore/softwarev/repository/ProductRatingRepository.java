package com.pymextore.softwarev.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pymextore.softwarev.model.Product;
import com.pymextore.softwarev.model.ProductRating;
import com.pymextore.softwarev.model.User;

public interface ProductRatingRepository extends JpaRepository<ProductRating, Long> {
    boolean existsByProductAndUser(Product product, User user);
}

