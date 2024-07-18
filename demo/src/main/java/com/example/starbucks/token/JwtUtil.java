package com.example.starbucks.token;


import com.example.starbucks.model.MemberCustom;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtUtil {

    private static final String SECRET_KEY ="asdfkjhasdliafnldsioahsigljasgsdfasdg";
    private static final SecretKey key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8)); //문자열을 키타입으로 바꿔줌

    public static String generateToken(MemberCustom memberCustom){
        return Jwts
                .builder()//키제작 만듬
                .issuedAt(new Date(System.currentTimeMillis()))//발급시간
                .expiration(new Date(System.currentTimeMillis() + 1000*5)) //기한시간
                .subject(memberCustom.getMemberId())
                .claim("userId",memberCustom.getMemberId())
                .signWith(key)
                .compact();
    }

    public static boolean validToken(String token){
        try {
            Jwts
                    .parser()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    public static Claims extractToken(String token){
        return Jwts
                .parser() //해석할게
                .verifyWith(key) //키줄게
                .build() //빌드할게
                .parseSignedClaims(token) //토큰을 줄테니 claim 해석할게
                .getPayload(); //payload 줄게
    }
}
