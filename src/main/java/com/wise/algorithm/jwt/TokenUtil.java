package com.wise.algorithm.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.spec.SecretKeySpec;
import java.util.Date;

public class TokenUtil {

    public static void main(String[] args) {
        String accessToken = generateAccessToken();
        System.out.println("access token:" + accessToken);

        System.out.println("check access token:" + checkAccessToken(accessToken));
    }

    /**
     * 生成访问令牌
     * @return
     */
    public static String generateAccessToken() {
        Date date = new Date();
        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setHeaderParam("alg", SignatureAlgorithm.HS256.getValue())
                // 签发时间
                .setIssuedAt(date)
                // 过期时间5分钟
                .setExpiration(new Date(date.getTime() + 5*60*1000))
                // 用户ID
                .setSubject("zhechu")
                .claim("platform", "ios")
                .claim("udid", "1")
                // 签发用户
                .setIssuer("server")
                .signWith(SignatureAlgorithm.HS256, new SecretKeySpec("app_secret".getBytes(), "AES"))
                .compact();
    }

    /**
     * 检查访问令牌是否有效
     * @param accessToken
     * @return
     */
    public static boolean checkAccessToken(String accessToken) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(new SecretKeySpec("app_secret".getBytes(), "AES"))
                    .parseClaimsJws(accessToken)
                    .getBody();
            System.out.println("claims:" + claims);

            String subject = claims.getSubject();
            System.out.println("subject:" + subject);

            return true;
        } catch (ExpiredJwtException e) {
            e.printStackTrace();
            System.out.println("令牌已过期");

            return false;
        }
    }

}
