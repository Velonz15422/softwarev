package com.pymextore.softwarev.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pymextore.softwarev.model.User;
import com.pymextore.softwarev.model.WishList;

@Repository
public interface WishListRepository extends JpaRepository<WishList, Integer> {

    List<WishList> findAllByUserOrderByCreatedDateDesc(User user);

}
