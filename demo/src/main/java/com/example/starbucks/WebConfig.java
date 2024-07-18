package com.example.starbucks;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                //http://localhost:3000 해당 주소로 들어온 사용자만
                .allowedOrigins("http://localhost:3000")

                //"GET", "POST", "PUT", "DELETE" 가능
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowedHeaders("*")
                .exposedHeaders("Authorization")
                .allowCredentials(true);
                //비밀번호 인증 or API key or
    }
}
