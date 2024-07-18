package com.example.starbucks.controller;

// 필요한 클래스들을 임포트합니다.
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

// 이 클래스가 컨트롤러임을 나타냅니다.
@Controller
public class UserController {

    // UserService 빈을 자동 주입합니다.
    @Autowired
    private UserService userService;

    // UserDetailServiceImpl 빈을 자동 주입합니다.
    @Autowired
    private UserDetailServiceImpl userDetailService;

    // PasswordEncoder 빈을 자동 주입합니다.
    @Autowired
    private PasswordEncoder passwordEncoder;

    // '/registry' POST 요청을 처리하는 메서드입니다.
    @PostMapping("/registry")
    public ResponseEntity<ApiResponse<String>> registerUser(@RequestBody UserCustom userCustom) {
        // 비밀번호를 암호화합니다.
        userCustom.setPassword(passwordEncoder.encode(userCustom.getPassword()));

        // 사용자 정보를 저장합니다.
        userService.saveUser(userCustom);

        // 성공 응답을 생성합니다.
        ApiResponse<String> apiResponse = new ApiResponse<>(Status.SUCCESS, "성공", null);
        return ResponseEntity.ok(apiResponse);
    }

    // '/login' POST 요청을 처리하는 메서드입니다.
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<String>> login(@RequestBody UserCustom userCustom) {
        try {
            // 사용자 정보를 로드합니다.
            UserDetails userDetails = userDetailService.loadUserByUsername(userCustom.getUserId());

            // 사용자 정보가 없거나 비밀번호가 일치하지 않으면 예외를 발생시킵니다.
            if (userDetails == null || !passwordEncoder.matches(userCustom.getPassword(), userDetails.getPassword())) {
                throw new BadCredentialsException("아이디가 없거나 비밀번호가 일치하지 않습니다.");
            }

            // 토큰을 생성합니다.
            String token =  JwtUtil.generateToken(userCustom);

            // HTTP 헤더를 생성하고 토큰을 추가합니다.
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.set("Authorization","Bearer"+token);

            // 성공 응답을 생성합니다.
            ApiResponse<String> apiResponse = new ApiResponse<>(Status.SUCCESS, "로그인 성공", token);
            return ResponseEntity.ok().headers(httpHeaders).body(apiResponse);

        } catch (UsernameNotFoundException e) {
            // 사용자를 찾을 수 없는 경우의 처리
            System.out.println("그런 아이디 없음");
            ApiResponse<String> apiResponse = new ApiResponse<>(Status.FAIL, "로그인 실패: 아이디 없음", null);
            return ResponseEntity.ok(apiResponse);
        } catch (BadCredentialsException e) {
            // 비밀번호가 일치하지 않는 경우의 처리
            System.out.println("비밀번호 오류");
            ApiResponse<String> apiResponse = new ApiResponse<>(Status.FAIL, "로그인 실패: 비밀번호 오류", null);
            return ResponseEntity.ok(apiResponse);
        }
    }
}