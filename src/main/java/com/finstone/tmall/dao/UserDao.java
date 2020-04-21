package com.finstone.tmall.dao;

import com.finstone.tmall.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDao extends JpaRepository<User, Integer> {

    User findByName(String name);

    User findByNameAndPassword(String name, String password);

}
