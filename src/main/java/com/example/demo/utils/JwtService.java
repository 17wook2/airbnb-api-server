package com.example.demo.utils;


import com.example.demo.config.Secret;
import com.example.demo.oAuth.UserDetailsServiceImpl;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtService {

    private final UserDetailsServiceImpl userDeatilsServiceImpl;

    private long tokenValidTime = 1000L * 60 * 30; // 30분
    private long refreshTokenValidTime = 1000L * 60 * 60 * 24 * 7;
    public String createJwt(String userEmail){
        Claims claims = Jwts.claims().setSubject(userEmail);
        Date now = new Date();
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + tokenValidTime))
                .signWith(SignatureAlgorithm.HS256, Secret.JWT_SECRET_KEY)
                .compact();
    }

    public String createRefreshToken() {
        Date now = new Date();
        return Jwts.builder()
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + refreshTokenValidTime))
                .signWith(SignatureAlgorithm.HS256, Secret.JWT_SECRET_KEY)
                .compact();
    }
    public Authentication getAuthentication(String token) {
        UserDetails userDetails = userDeatilsServiceImpl.loadUserByUsername(getMemberEmail(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }
    public String getMemberEmail(String token) {
        try {
            return Jwts.parser().setSigningKey(Secret.JWT_SECRET_KEY).parseClaimsJws(token).getBody().getSubject();
        } catch(ExpiredJwtException e) {
            return e.getClaims().getSubject();
        }
    }

    public String resolveToken(HttpServletRequest req){
        return req.getHeader("X-ACCESS-TOKEN");
    }

    public boolean validateTokenExceptExpiration(String token) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(Secret.JWT_SECRET_KEY).parseClaimsJws(token);
            return !claims.getBody().getExpiration().before(new Date());
        } catch(Exception e) {
            return false;
        }
    }


}
