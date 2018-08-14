package com.example.demo.service;


import com.example.demo.model.User;

import java.util.List;

public interface UserService {
    public User getUserById(int userId);

    boolean addUser(User record);

    List<User> selectTest();
}