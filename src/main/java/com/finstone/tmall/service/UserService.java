package com.finstone.tmall.service;

import com.finstone.tmall.dao.UserDao;
import com.finstone.tmall.pojo.Page4JPA;
import com.finstone.tmall.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserDao userDao;

    public Page4JPA<User> list(Integer start, Integer size){
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        Pageable pageable = new PageRequest(start, size, sort);
        Page<User> page = userDao.findAll(pageable);
        Page4JPA<User> us = new Page4JPA<>(page);
        return us;
    }

    public User get(Integer id){
        return userDao.findOne(id);
    }

    //检查登录用户名+密码
    public User get(String name, String password){
        return userDao.findByNameAndPassword(name, password);
    }

    public User add(User user){
        return userDao.save(user);
    }

    public void delete(Integer id){
        userDao.delete(id);
    }

    public User update(User user){
        return userDao.save(user);
    }

    //判断用户名是否被占用
    public boolean isExist(String name){
        User user = userDao.findByName(name);
        return user!=null;
    }

}
