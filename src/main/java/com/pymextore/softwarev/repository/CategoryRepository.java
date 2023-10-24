package com.pymextore.softwarev.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pymextore.softwarev.model.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer > {
    
}
