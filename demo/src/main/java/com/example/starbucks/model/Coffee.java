package com.example.starbucks.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor      //new Coffee()
@AllArgsConstructor     //new Coffee(id,name,price)
@RequiredArgsConstructor//new Coffee(name,price) by NonNull
@Entity
@Table(name = "coffee") //안써도 되지만 써야지 확실히 전달됨
public class Coffee {
    //DB key 설정 및 자동증가
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;
    @NonNull
    private String name;
    @NonNull
    private int price;
}
