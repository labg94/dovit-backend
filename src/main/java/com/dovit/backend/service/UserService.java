package com.dovit.backend.service;

import com.dovit.backend.domain.User;

import java.util.List;

public interface UserService {

    User findById(Long id);

    List<User> findAll();

    User updateUser(User user);
}
