//package com.example.cellphoneweb.jwt;
//
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;
//import io.jsonwebtoken.io.Decoders;
//import io.jsonwebtoken.security.Keys;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Component;
//
//import javax.crypto.SecretKey;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.Map;
//
//@Component
//public class JwtHelper {
//
//    @Value("${token.key}")
//    private String strKey;
//
//    @Value("${token.expiryHour}")
//    private long expiryHour;
//
//    @Value("${token.expiryDay}")
//    private long expiryDay;
//
//    @Value("${token.refreshKey}")
//    private String refreshKey;
//
//    public String generateToken(String username) {
//        Map<String, Object> claims = new HashMap<>();
//        return createToken(claims, username);
//    }
//
//    private String createToken(Map<String, Object> claims, String subject) {
//        long nowMillis = System.currentTimeMillis();
////        long expiryMillis = nowMillis + expiryHour * 3600 * 1000; // expiry in hours
//        long expiryMillis = nowMillis + 10 * 1000;
//        return Jwts.builder()
//                .setClaims(claims)
//                .setSubject(subject)
//                .setIssuedAt(new Date(nowMillis))
//                .setExpiration(new Date(expiryMillis))
//                .signWith(SignatureAlgorithm.HS256, strKey)
//                .compact();
//    }
//
//    public boolean validateToken(String token, String username) {
//        final String extractedUsername = extractUsername(token);
//        return (extractedUsername.equals(username) && !isTokenExpired(token));
//    }
//
//    public boolean isTokenExpired(String token) {
//        return extractExpiration(token).before(new Date());
//    }
//
//    private Date extractExpiration(String token) {
//        return Jwts.parser().setSigningKey(strKey).parseClaimsJws(token).getBody().getExpiration();
//    }
//
//    private String extractUsername(String token) {
//        return Jwts.parser().setSigningKey(strKey).parseClaimsJws(token).getBody().getSubject();
//    }
//
//    // Optional: Refresh token generation
//    public String generateRefreshToken(String username) {
//        Map<String, Object> claims = new HashMap<>();
//        long nowMillis = System.currentTimeMillis();
//        long expiryMillis = nowMillis + expiryDay * 24 * 3600 * 1000; // expiry in days
////        long expiryMillis = nowMillis + 10 * 1000;
//        return Jwts.builder()
//                .setClaims(claims)
//                .setSubject(username)
//                .setIssuedAt(new Date(nowMillis))
//                .setExpiration(new Date(expiryMillis))
//                .signWith(SignatureAlgorithm.HS256, refreshKey)
//                .compact();
//    }
//
//    // Check if refresh token is expired
//    public boolean isRefreshTokenExpired(String token) {
//        return extractRefreshExpiration(token).before(new Date());
//    }
//
//    private Date extractRefreshExpiration(String token) {
//        return Jwts.parser().setSigningKey(refreshKey).parseClaimsJws(token).getBody().getExpiration();
//    }
//
//    public Claims decodeToken(String token) {
//        return Jwts.parser()
//                .setSigningKey(strKey)
//                .parseClaimsJws(token)
//                .getBody();
//    }
//
//    public Claims decodeRefreshToken(String refreshToken) {
//        return Jwts.parser()
//                .setSigningKey(refreshKey)
//                .parseClaimsJws(refreshToken)
//                .getBody();
//    }
//}
