package com.example.starbucks;



//Spring mvc -> Controller
//Spring jpa -> repository
//spring security -> SecurityConfig 설정


import com.example.starbucks.token.JWTFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private JWTFilter jwtFilter;

    // 패스워드 암호화해주는 함수
    // pw:qwer1234 -> pw:abc! (A사람)
    // pw:qwer1234 -> pw:bbc! (B사람) 즉 다른사람이 비밀번호를 같게 설정해도 DB에는 다르게 저장된다.

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    //securityFilterChain -> Http 요청을 처리 할 때, 적용되는 보안필터(인증, 권한부여, 세션)
    //HttpSecurity -> Http 보안 구성해주는 객체[권한부여 규칙, Form 태크 설정,,,......]
    @Bean
    public DefaultSecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        //csrf(사이트 간 요청 위조) -> 기본적인 설정이 on이 되어있다.
        http
                .csrf(x->x.disable()) //csrf off 설정
                .sessionManagement(x-> x.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) //세션 기반 안함설정
                        .authorizeHttpRequests(x->
                                x.requestMatchers("**").permitAll() //누구든지 우리의 starbucks 모든(**) 파일 접근가능(permitAll)
                                .anyRequest().authenticated())//아무 request를 인증해야함
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        //atomic 패턴
        //component -> atom(하나)/ molecules(두개) props두단계 /organism(3개이상)
        return http.build();
    }
}
