package com.example.starbucks.controller;

import com.example.starbucks.dto.ApiResponse;
import com.example.starbucks.model.UserCustom;
import com.example.starbucks.service.UserDetailServiceImpl;
import com.example.starbucks.service.UserService;
import com.example.starbucks.status.Status;
import com.example.starbucks.token.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserDetailServiceImpl userDetailService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/registry")
    public ResponseEntity<ApiResponse<String>> registerUser(@RequestBody UserCustom userCustom) {
        // 아이디 유효성 검사, 중복 조회, 비밀번호 암호화
        userCustom.setPassword(passwordEncoder.encode(userCustom.getPassword()));

        // 저장
        userService.saveUser(userCustom);

        ApiResponse<String> apiResponse = new ApiResponse<>(Status.SUCCESS, "성공", null);
        return ResponseEntity.ok(apiResponse);
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<String>> login(@RequestBody UserCustom userCustom) {
        try {
            UserDetails userDetails = userDetailService.loadUserByUsername(userCustom.getUserId());

            // 가져온 ID가 NULL 이거나 POST로 보낸 비번이랑 일치하지 않으면 예외 발생
            if (userDetails == null || !passwordEncoder.matches(userCustom.getPassword(), userDetails.getPassword())) {
                throw new BadCredentialsException("아이디가 없거나 비밀번호가 일치하지 않습니다.");
            }

            // 성공
            //토큰발급
            String token =  JwtUtil.generateToken(userCustom);

            //response header (토큰은 헤더에 숨기는거임)
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.set("Authorization","Bearer"+token);

            //response body
            ApiResponse<String> apiResponse = new ApiResponse<>(Status.SUCCESS, "로그인 성공", token);
            return ResponseEntity.ok().headers(httpHeaders).body(apiResponse);

        } catch (UsernameNotFoundException e) {
            System.out.println("그런 아이디 없음");
            ApiResponse<String> apiResponse = new ApiResponse<>(Status.FAIL, "로그인 실패: 아이디 없음", null);
            return ResponseEntity.ok(apiResponse);
        } catch (BadCredentialsException e) {
            System.out.println("비밀번호 오류");
            ApiResponse<String> apiResponse = new ApiResponse<>(Status.FAIL, "로그인 실패: 비밀번호 오류", null);
            return ResponseEntity.ok(apiResponse);
        }
    }
}
