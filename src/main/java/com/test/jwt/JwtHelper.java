package com.test.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

/**
 * 基于JWT可以做统一登录和验证   可以在过滤器对token进行校验 如果token解析失败或者过期了则禁止访问
 *
 * @author lz-119612
 * @version 1.0
 * @date 2020/7/1 15:28
 *  
 **/
public class JwtHelper {
    private final static String base64Secret = "MDk4ZjZiY2Q0NjIxZDM3M2NhZGU0ZTgzMjYyN2I0ZjY=";
    private final static int expiresSecond = 3600000;

    public static Claims parseJWT(String jsonWebToken) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(DatatypeConverter.parseBase64Binary(base64Secret))
                    .parseClaimsJws(jsonWebToken).getBody();
            return claims;
        } catch (Exception ex) {
            return null;
        }
    }

    public static String createJWT(String username, String roles, String privileges) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS384;

        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        //生成签名密钥
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(base64Secret);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

        //添加构成JWT的参数
        JwtBuilder builder = Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setSubject(username)
                .setIssuedAt(now) //获取jwt发布时间
                .claim("user_name", username)
                .claim("user_role", roles)
                .claim("user_privilege", privileges)
                .signWith(signatureAlgorithm, signingKey);
        //添加Token过期时间
        if (expiresSecond >= 0) {
            long expMillis = nowMillis + expiresSecond;
            Date exp = new Date(expMillis);
            builder.setExpiration(exp)
                    //.compact()
                    .setNotBefore(now);
        }

        //生成JWT
        return builder.compact();
    }

    /**
     * 验证token是否过期失效
     * @param expirationTime
     * @return
     */
    public boolean isTokenExpired (Date expirationTime) {
        return expirationTime.before(new Date());
    }
    /**
     * 获取token失效时间
     * @param token
     * @return
     */
    public static Date getExpirationDateFromToken(String token) {
        return parseJWT(token).getExpiration();
    }
    /**
     * 获取用户名从token中
     */
    public static String getUsernameFromToken(String token) {
        return parseJWT(token).getSubject();
    }

    /**
     * 获取jwt发布时间
     */
    public static Date getIssuedAtDateFromToken(String token) {
        return parseJWT(token).getIssuedAt();
    }

    public static void main(String[] args) {
        String token = createJWT("lz", "role", "read");
        System.out.println(token);
        Claims claims = parseJWT(token);
        System.out.println(claims);
        System.out.println("----------------------------------------------------------------------");
        System.out.println(getExpirationDateFromToken(token));
        System.out.println(getUsernameFromToken(token));
        System.out.println(getIssuedAtDateFromToken(token));

        System.out.println(LocalDateTime.now().toEpochSecond(ZoneOffset.of("+8")));
        System.out.println(System.currentTimeMillis());
        System.out.println(LocalDateTime.now().toEpochSecond(ZoneOffset.UTC));


    }

}
