package com.example.starbucks.service;

import com.example.starbucks.model.MemberCustom;
import com.example.starbucks.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberServiceImpl implements MemberService{
    @Autowired
    private MemberRepository memberRepository;


    @Override
    public void saveMember(MemberCustom memberCustom) {
        memberRepository.save(memberCustom);
    }
}
