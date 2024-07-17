package com.example.starbucks.repository;

import com.example.starbucks.model.UserCustom;
import org.springframework.data.jpa.repository.JpaRepository;

//Repository : DB와 연결 해주는 클래스
public interface UserRepository extends JpaRepository<UserCustom,Integer>{
    UserCustom findByUserId(String userId);
}
