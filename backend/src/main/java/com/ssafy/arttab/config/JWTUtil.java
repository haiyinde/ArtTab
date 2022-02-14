package com.ssafy.arttab.config;

import com.ssafy.arttab.exception.authorization.InvalidTokenException;
import com.ssafy.arttab.exception.authorization.TokenExpiredException;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;

/**
 * @packageName : com.ssafy.arttab.config
 * @fileName : JWTUtil
 * @date : 2022-02-07
 * @language : JAVA
 * @classification :
 * @time_limit : 2sec
 * @required_time : 00:40 ~ 01:22
 * @submissions : 1
 * @description :
 **/
@RequiredArgsConstructor
@Component
public class JWTUtil {

        private String secretKey = "banban";
        private long tokenValidTime = 2*60 * 60 * 1000L;
        private JwtParser jwtParser = Jwts.parser().setSigningKey(secretKey);

        //토큰 생성
        public String createToken(Map<String, Object> payload) {

                //payload 부분 설정
                Claims claims = Jwts.claims(payload);

                Date ext = new Date(); // 토큰 만료 시간
                Date validity = new Date(ext.getTime() + tokenValidTime);

                // 토큰 Builder
                String jwt = Jwts.builder()
                        .setClaims(claims) // Claims 설정
                        .setIssuedAt(ext)
                        .setExpiration(validity) // 토큰 만료 시간 설정
                        .signWith(SignatureAlgorithm.HS256, secretKey.getBytes()) // HS256과 Key로 Sign
                        .compact(); // 토큰 생성

                return jwt;
        }

        //토큰 검증
        public void validateToken(String token) {
                try {
                        jwtParser.parseClaimsJws(token);
                } catch (ExpiredJwtException e) {       //토큰 만료
                        throw new TokenExpiredException();
                } catch (JwtException e) {
                        throw new InvalidTokenException();
                }
        }


}
