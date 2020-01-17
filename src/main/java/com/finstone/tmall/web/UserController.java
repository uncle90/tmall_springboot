package com.finstone.tmall.web;

import com.finstone.tmall.pojo.Page4JPA;
import com.finstone.tmall.pojo.User;
import com.finstone.tmall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/users")
    public Page4JPA<User> list(
            @RequestParam(name="start", defaultValue = "0") int start,
            @RequestParam(name="size", defaultValue = "" + Page4JPA.defaultSize) int size)
            throws Exception{
        Page4JPA<User> us = userService.list(start, size);
        return us;
    }

    @PostMapping("/users")
    public User add(User user) throws Exception{
        return userService.add(user);
    }

    @DeleteMapping("/users/{id}")
    public Object delete(@PathVariable("id") int id) throws Exception{
        userService.delete(id);
        return null;
    }

    @PutMapping("/users")
    public User update(User user) throws Exception{
        return userService.update(user);
    }

    @GetMapping("/users/{id}")
    public User get(@PathVariable("id") int id) throws Exception{
        return userService.get(id);
    }

}
