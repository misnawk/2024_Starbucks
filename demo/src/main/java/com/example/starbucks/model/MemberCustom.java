package com.example.starbucks.model;


import jakarta.persistence.*;
import lombok.*;

import javax.management.relation.Role;

@Data
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name = "member")

public class MemberCustom {
    //해당 필드가 테이블의 기본 키 (primary key) 라는 것을 나타냄
    @Id
    // 기본키가 자동으로 증가되는 방식임
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int member_idx;

    @NonNull
    @Column(name = "member_name")
    private String memberName;

    @NonNull
    @Column(name = "member_id")
    private String memberId;

    @NonNull
    @Column(name = "member_pwd")
    private String memberPwd;

    @NonNull
    @Column(name = "member_email")
    private String memberEmail;


    @NonNull
    @Column(name = "member_phone")
    private String memberPhone;


    @NonNull
    @Column(name = "member_address")
    private String memberAddress;

    public enum MemberRole {
        USER, ADMIN
    }
}
