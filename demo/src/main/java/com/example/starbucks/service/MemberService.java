package com.example.starbucks.service;

import com.example.starbucks.model.MemberCustom;
import org.springframework.stereotype.Service;

@Service
public interface MemberService {
    void saveMember(MemberCustom memberCustom);
}
