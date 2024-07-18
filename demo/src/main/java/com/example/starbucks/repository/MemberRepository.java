package com.example.starbucks.repository;

import com.example.starbucks.model.MemberCustom;
import com.example.starbucks.service.MemberService;
import com.example.starbucks.service.MemberServiceImpl;
import org.springframework.data.jpa.repository.JpaRepository;

//Repository : DB와 연결 해주는 클래스
public interface MemberRepository extends JpaRepository<MemberCustom,Integer>{
    MemberCustom findByMemberId(String memberId);
}
