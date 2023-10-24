package com.pymextore.softwarev.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pymextore.softwarev.model.Product;


@Repository
public interface ProductRepository extends JpaRepository<Product, Integer >  {
    
}
