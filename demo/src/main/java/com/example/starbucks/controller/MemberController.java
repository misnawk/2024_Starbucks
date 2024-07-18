package com.example.starbucks.controller;

// 필요한 클래스들을 임포트합니다.

import com.example.starbucks.dto.ApiResponse;
import com.example.starbucks.model.MemberCustom;
import com.example.starbucks.service.MemberDetailServiceImpl;
import com.example.starbucks.service.MemberService;
import com.example.starbucks.status.Status;
import com.example.starbucks.token.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
//이 컨트롤러의 모든 엔드포인트는 "/api" 경로로 시작함
@RequestMapping("")

public class MemberController {

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberDetailServiceImpl userDetailService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("api/signup")

    public ResponseEntity<ApiResponse<String>> registerUser(@RequestBody MemberCustom memberCustom) {
        //@RequestBody는 HTTP요청 본문의 json 데이터를 MemberCustom 객체로 반환한다.

        memberCustom.setMemberPwd(passwordEncoder.encode(memberCustom.getMemberPwd()));
        //사용자가 입력한 비밀번호를 가져와서 passwordEncoder 를 사용해서 암호화한다.

        memberService.saveMember(memberCustom);
        //암호화된 비밀번호를 다시 memberCustom 객체에 설정한다.

        ApiResponse<String> apiResponse = new ApiResponse<>(Status.SUCCESS, "성공", null);
        //ApiResponse 객체를 생성한다.
        //상태는 SUCCESS, 상태는 SUCCESS, 메세지는 "성공" 데이터는 Null 이다.
        return ResponseEntity.ok(apiResponse);
    }

    @PostMapping("/login")

    public ResponseEntity<ApiResponse<String>> login(@RequestBody MemberCustom memberCustom) {
        try {
            //로그인 시도중인 사용자가 입력한 ID를 가져와서 사용자의 정보를 검색해봄
            UserDetails userDetails = userDetailService.loadUserByUsername(memberCustom.getMemberId());

            //만약 사용자가 존재하지 않거나
            //passwordEncoder은 입력된 비밀번호를 암호화하여 DB에 있는 비밀번호와 비교하여 같은지 확인한다.
            if (userDetails == null || !passwordEncoder.matches(memberCustom.getMemberPwd(), userDetails.getPassword())) {
                throw new BadCredentialsException("아이디가 없거나 비밀번호가 일치하지 않습니다.");
            }

            //성공
            //토큰 발급
            String token = JwtUtil.generateToken(memberCustom);

            //헤더만듬
            HttpHeaders httpHeaders = new HttpHeaders();
            //토큰 머리에 딱 꼽음
            httpHeaders.set("Authorization", "Bearer " + token);

            Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);

            ApiResponse<String> apiResponse = new ApiResponse<>(Status.SUCCESS, "로그인 성공", token);
            return ResponseEntity.ok().headers(httpHeaders).body(apiResponse);

        } catch (UsernameNotFoundException e) {
            ApiResponse<String> apiResponse = new ApiResponse<>(Status.FAIL, "로그인 실패: 아이디 없음", null);
            return ResponseEntity.ok(apiResponse);
        } catch (BadCredentialsException e) {
            ApiResponse<String> apiResponse = new ApiResponse<>(Status.FAIL, "로그인 실패: 비밀번호 오류", null);
            return ResponseEntity.ok(apiResponse);
        }
    }
}
