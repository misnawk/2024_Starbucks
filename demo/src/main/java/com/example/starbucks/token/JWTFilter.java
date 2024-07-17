package com.example.starbucks.token;

import com.example.starbucks.service.UserDetailServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JWTFilter extends OncePerRequestFilter {
    private JwtUtil jwtUtil;
    private UserDetailServiceImpl userDetailService;

    public JWTFilter(UserDetailServiceImpl userDetailService, JwtUtil jwtUtil) {
        this.userDetailService = userDetailService;
        this.jwtUtil = jwtUtil;
    }

    //스프링부트에 요청하면 무조건 걸치는 입구 (요청이있으면 무조건 거치는 구간임)
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String authorizationHeader = request.getHeader("Authorization");
        //Bearer
        boolean headerIsEmpty = authorizationHeader == null;
        boolean doNotHaveBearer = !authorizationHeader.startsWith("Bearer");

        //token 아예없음
        if(headerIsEmpty || doNotHaveBearer){
            return;
        }

        //Bearer sdfklnslgdnjfglksjf
        String token = authorizationHeader.substring(7);
        //token 유효기간 지남
        if(jwtUtil.validToken(token)){
            return;
        }

        // token 정상적
        String name = jwtUtil.extractToken(token).getSubject();

        // 


        }

    }



