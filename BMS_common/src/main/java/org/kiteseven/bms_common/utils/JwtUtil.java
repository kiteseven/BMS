package org.kiteseven.bms_common.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Map;

public class JwtUtil {

    public static String createJWT(String secretKeyString, Map<String, Object> claims) {
        // 创建 SecretKey
        SecretKey secretKey = Keys.hmacShaKeyFor(secretKeyString.getBytes(StandardCharsets.UTF_8));

        // 设置过期时间
//        long expMillis = System.currentTimeMillis() + ttlMillis;
//        Date exp = new Date(expMillis);

        // 构建 JWT
        JwtBuilder builder = Jwts.builder()
                .claims(claims)
                .issuedAt(new Date())
                .signWith(secretKey);


        return builder.compact();
    }
    public static Claims parseJWT(String secretKeyString, String token) {
        // 创建 SecretKey
        SecretKey secretKey = Keys.hmacShaKeyFor(secretKeyString.getBytes(StandardCharsets.UTF_8));

        // 解析 JWT
        Claims claims = Jwts.parser()
                .setSigningKey(secretKey) // 设置用于验证签名的密钥
                .build()
                .parseClaimsJws(token) // 解析 JWT
                .getBody(); // 获取声明 (Claims)

        return claims;
    }
}
