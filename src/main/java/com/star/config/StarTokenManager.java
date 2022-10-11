package com.star.config;

import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author liuxing
 * @description token 管理器
 * @create: 2022-10-10 23:11
 */
@Component
public class StarTokenManager {

    // 一天
    private Long tokenExpiration = 24 * 60 * 60 * 1000L;

    /**
     * @return String
     * @Author liuxing
     * @Description 生成token
     * @Date 23:16 2022/10/7
     * @Param []
     */
    public String createToken(String username, String secretKey) {
        String token = Jwts.builder().setSubject(username).setIssuer("liu")
                .setExpiration(new Date(System.currentTimeMillis() + tokenExpiration))
                .signWith(SignatureAlgorithm.HS512, secretKey).compressWith(CompressionCodecs.GZIP).compact();
        return token;
    }

    public String parserToken(String token, String secretKey) {
        String subject = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
        return subject;
    }


}
