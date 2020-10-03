package com.wise.algorithm.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * OIDC 协议
 *
 * @author lingyuwang
 * @date 2020-10-03 23:32
 * @since 1.0.4
 */
public class OidcUtil {

    /**
     * 秘钥
     */
    public final static String sharedTokenSecret ="hellooauthhellooauthhellooauthhellooauth";

    public static void main(String[] args) {
        String idToken = genrateIdToken("po", "zhechu");
        System.out.println("id token:" + idToken);

        System.out.println("check id token:" + checkIdToken(idToken));
    }

    /**
     * 生成 ID_TOKEN
     * @param appId
     * @param user
     * @return
     */
    public static String genrateIdToken(String appId,String user){
        // ID令牌的头部信息
        Map<String, Object> headerMap = new HashMap<>();
        headerMap.put("typ", "JWT");
        headerMap.put("alg", SignatureAlgorithm.HS256.getValue());

        // ID令牌的主体信息
        Map<String, Object> payloadMap = new HashMap<>();
        payloadMap.put("iss", "http://localhost:8081/");
        payloadMap.put("sub", user);
        payloadMap.put("aud", appId);
        Date date = new Date();
        payloadMap.put("iat", date);
        payloadMap.put("exp", new Date(date.getTime() + 5*60*1000));

        // 采用HS256算法
        Key key = new SecretKeySpec(sharedTokenSecret.getBytes(), SignatureAlgorithm.HS256.getJcaName());

        return Jwts.builder().setHeaderParams(headerMap).setClaims(payloadMap).signWith(SignatureAlgorithm.HS256, key).compact();
    }

    /**
     * 检查 ID_TOKEN
     * @param idToken
     * @return
     */
    public static boolean checkIdToken(String idToken) {
        try {
            // 采用HS256算法
            SecretKeySpec key = new SecretKeySpec(sharedTokenSecret.getBytes(), SignatureAlgorithm.HS256.getJcaName());
            Claims claims = Jwts.parser()
                    .setSigningKey(key)
                    .parseClaimsJws(idToken)
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
