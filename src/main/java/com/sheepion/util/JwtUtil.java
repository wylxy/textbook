package com.sheepion.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

import java.security.Key;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Slf4j
public class JwtUtil {
    //生成随机HS256秘钥
    private static final Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    public static String generateToken(Integer id, Byte roleId, List<String> permissions) {
        log.debug("生成token：{} {} {}", id, roleId, permissions);
        if (permissions == null) {
            permissions = new ArrayList<>();
        }
        HashMap<String,Object> claims= new HashMap<>();
        claims.put("id",id);
        claims.put("roleId",roleId);
        claims.put("permissions",permissions);
        claims.put("created",new java.util.Date());
        String token = Jwts.builder()
                .setClaims(claims)
                .signWith(SECRET_KEY, SignatureAlgorithm.HS256)
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 7))
                .compact();
        log.debug("生成token：{}", token);
        return token;
    }
    public static Boolean isTokenExpired(String token){
        return parseToken(token).getExpiration().before(new Date());
    }
    public static Integer getUserId(String token){
        return (Integer) parseToken(token).get("id");
    }
    public static Byte getRoleId(String token){
        return  ((Integer)parseToken(token).get("roleId")).byteValue();
    }
    public static List<String> getPermissions(String token){

        Object permissions = parseToken(token).get("permissions");
        if (permissions instanceof List<?>) {
            return (List<String>) permissions;
        }
        throw new ClassCastException("token中的permissions不是List<String>类型");
    }
    public static Claims parseToken(String token){
        return io.jsonwebtoken.Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
