package com.example.starbucks.repository;

// Rest API 개발
// 클라이언트 -> 서버[데이터]
// Controller[경로 지정]
// Repository[DB에서 데이터 가져오는 역할]


import com.example.starbucks.model.Coffee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

// JpaRepository<엔티티, 키객체>
// 클래스 <-> 대응하는 sql 테이블 객체를 엔티티라고함
public interface CoffeeRepository extends JpaRepository<Coffee, Integer> {

    List<Coffee> findByName(String name);

    @Query("select c from Coffee c where c.price between :min and :max")
    List<Coffee> findByPrice(@Param("min") int min, @Param("max") int max);
}
