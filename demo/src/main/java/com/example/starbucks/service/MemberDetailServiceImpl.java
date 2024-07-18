package com.example.starbucks.service;

import com.example.starbucks.model.MemberCustom;
import com.example.starbucks.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.lang.reflect.Member;
import java.util.Collections;

//UserDetailsService는 spring이 제공하는 함수임
//사용자 로그인시 사용자 정보를 조회하고, 사용자 인증을 처리하는데 사용됨
@Service
public class MemberDetailServiceImpl implements UserDetailsService {

    private MemberRepository memberRepository;

    @Autowired
    public MemberDetailServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String memberId) throws UsernameNotFoundException {
        MemberCustom memberCustom = memberRepository.findByMemberId(memberId);

        //memberName 없다면
        if(memberCustom == null) throw new UsernameNotFoundException("there is no User");
        return new User(memberCustom.getMemberId(),memberCustom.getMemberPwd(),Collections.emptyList());
    }
}
