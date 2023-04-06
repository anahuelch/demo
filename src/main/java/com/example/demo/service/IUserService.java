package com.example.demo.service;

import com.example.demo.model.User;

public interface IUserService {
    User createUser(User user) throws Exception;
    User getUser(Long id);
    boolean deleteUser(Long id);
    boolean isAValidEmail(String emailAddress);
    boolean isAValidPassword(String password);
}
