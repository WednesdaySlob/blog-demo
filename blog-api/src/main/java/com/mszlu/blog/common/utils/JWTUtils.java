package com.mszlu.blog.common.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.crypto.SecretKey;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * JWT 工具类 (JJWT 0.11.5+ 版本) 注意：与 0.9.x 版本 API 完全不同！
 */
@Getter
@Component
public class JWTUtils {

  // 有效期（单位：毫秒）24小时
  private static final long EXPIRATION_TIME = 24 * 60 * 60 * 1000;

  @Value("${jwt.secret-key}")
  private  String  secretKey;

  /**
   * 生成密钥 注意：secretKey 需要至少有 32 个字符（256位）
   */
  private static SecretKey getSecretKey(String secretKey) {
    // 转换为字节数组
    byte[] keyBytes = secretKey.getBytes();
    // 生成安全的密钥
    return Keys.hmacShaKeyFor(keyBytes);
  }

  /**
   * 生成 Token
   *
   * @param userId    用户ID
   * @param secretKey 密钥（至少32位）
   * @return JWT Token
   */
  public  String createToken(Long userId, String secretKey) {
    if (userId == null || !StringUtils.hasText(secretKey)) {
      return null;
    }

    // 创建 payload
    Map<String, Object> claims = new HashMap<>();
    claims.put("userId", userId);

    // 生成密钥
    SecretKey key = getSecretKey(secretKey);

    // 签发时间
    Date now = new Date();
    // 过期时间
    Date expiration = new Date(now.getTime() + EXPIRATION_TIME);

    // 创建 JWT
    return Jwts.builder()
        // 载荷
        .setClaims(claims)
        // 签发时间
        .setIssuedAt(now)
        // 过期时间
        .setExpiration(expiration)
        // 签名算法和密钥（自动选择HS256）
        .signWith(key)
        // 生成Token
        .compact();
  }

  /**
   * 解析 Token
   *
   * @param token     JWT Token
   * @param secretKey 密钥
   * @return 用户ID
   */
  public static Long getUserIdFromToken(String token, String secretKey) {
    if (!StringUtils.hasText(token) || !StringUtils.hasText(secretKey)) {
      return null;
    }

    try {
      SecretKey key = getSecretKey(secretKey);

      // 解析 Token
      Jws<Claims> claimsJws = Jwts.parserBuilder()
          // 设置签名密钥
          .setSigningKey(key)
          // 构建解析器
          .build()
          // 解析 Token
          .parseClaimsJws(token);

      // 获取载荷
      Claims claims = claimsJws.getBody();

      // 提取用户ID
      Object userIdObj = claims.get("userId");
      if (userIdObj instanceof Integer) {
        return ((Integer) userIdObj).longValue();
      } else if (userIdObj instanceof Long) {
        return (Long) userIdObj;
      } else {
        return null;
      }
    } catch (JwtException e) {
      // Token 无效、过期、签名错误等
      e.printStackTrace();
      return null;
    }
  }

  /**
   * 验证 Token
   *
   * @param token     JWT Token
   * @param secretKey 密钥
   * @return 是否有效
   */
  public static boolean validateToken(String token, String secretKey) {
    if (!StringUtils.hasText(token) || !StringUtils.hasText(secretKey)) {
      return false;
    }

    try {
      SecretKey key = getSecretKey(secretKey);

      Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
      // 解析成功，Token有效
      return true;
    } catch (JwtException e) {
      // Token 无效
      return false;
    }
  }

  /**
   * 获取 Token 信息
   *
   * @param token     JWT Token
   * @param secretKey 密钥
   * @return Claims 对象
   */
  public static Claims getClaimsFromToken(String token, String secretKey) {
    if (!StringUtils.hasText(token) || !StringUtils.hasText(secretKey)) {
      return null;
    }

    try {
      SecretKey key = getSecretKey(secretKey);

      return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    } catch (JwtException e) {
      e.printStackTrace();
      return null;
    }
  }

  /**
   * 验证token
   * @param token
   * @param secretKey
   * @return
   */
  public static Map<String, Object> checkToken(String token, String secretKey) {
    if (!StringUtils.hasText(token) || !StringUtils.hasText(secretKey)) {
      return null;
    }

    try {
      SecretKey key = getSecretKey(secretKey);
      Jws<Claims> jws = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
      return jws.getBody();
    } catch (JwtException e) {
      // 可以选择性地记录日志
      return null;

    }
  }


  public static String generateRandomSecretKey() {
    // 32字节 = 256位
    SecureRandom random = new SecureRandom();
    byte[] key = new byte[32];
    random.nextBytes(key);
    return Base64.getEncoder().encodeToString(key);
  }

//  public static void main(String[] args) {
//    System.out.println(generateRandomSecretKey());
//  }

}