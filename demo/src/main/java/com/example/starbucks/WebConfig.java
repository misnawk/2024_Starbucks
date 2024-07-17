package com.example.starbucks;


import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;



@ Configuration
public class WebConfig implements WebMvcConfigurer {

    //CORS: 교차 요청 안됨 원칙임
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") //모든 주소 적용됨
                .allowedOrigins("http://localhost:3000") //해당 주소에 해당하는 사람만 허용함
                .allowedMethods("GET","POST","PUT","DELETE") //Https request 메서드 허용함
                .allowedHeaders("*") //request의 모든 header 허용
                .allowedHeaders("Authorization") // 클라이언트가 Authorization은 볼수있게 해줌
                .allowCredentials(true); //클라이언트 관련 메서드임
    }
}
