package com.example.starbucks.service;

import com.example.starbucks.model.UserCustom;
import com.example.starbucks.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserRepository userRepository;


    @Override
    public void saveUser(UserCustom userCustom) {
        userRepository.save(userCustom);
    }
}
