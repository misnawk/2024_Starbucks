package com.example.starbucks.service;

import com.example.starbucks.model.UserCustom;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    void saveUser(UserCustom userCustom);
}
