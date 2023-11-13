package com.example.restauth.config.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;
import java.util.function.Function;

@Component
public class JwtUtil implements Serializable {

  private static final long serialVersionUID = -2143158467122881175L;
  private static final long EXPIRATION_TIME = 86400000;

  @Value("${jwt.secret}")
  private String secretKey;

  /**
   * Get username from token
   *
   * @param token given token
   * @return a {@link String} name
   */
  public String getUsernameFromToken(final String token) {
    return getClaimFromToken(token, Claims::getSubject);
  }

  /**
   * Get expire date from token
   *
   * @param token given token
   * @return a {@link Date} date
   */
  public Date getExpirationDateFromToken(final String token) {
    return getClaimFromToken(token, Claims::getExpiration);
  }

  /**
   * Get claim from token
   *
   * @param token given token
   * @param claimsResolver given resolver
   * @return {@link T} object
   */
  public <T> T getClaimFromToken(final String token, Function<Claims, T> claimsResolver) {
    final Claims claims = getAllClaimsFromToken(token);
    return claimsResolver.apply(claims);
  }

  /**
   * Get all claims form token
   *
   * @param token given token
   * @return {@link Claims} object
   */
  private Claims getAllClaimsFromToken(final String token) {
    return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
  }

  /**
   * Check token is expired or not
   *
   * @param token given token
   * @return a {@link Boolean} value
   */
  private Boolean isTokenExpired(final String token) {
    final Date expiration = getExpirationDateFromToken(token);
    return expiration.before(new Date());
  }

  /**
   * Generate Token
   *
   * @param userDetails given user details
   * @return a {@link String} generated token
   */
  public String generateToken(final UserDetails userDetails) {
    final Date now = new Date();
    final Date expiryDate = new Date(now.getTime() + EXPIRATION_TIME);

    return Jwts.builder()
        .setSubject(userDetails.getUsername())
        .setIssuedAt(new Date())
        .setExpiration(expiryDate)
        .signWith(SignatureAlgorithm.HS512, secretKey)
        .compact();
  }

  /**
   * Validate Token
   *
   * @param token given token
   * @param userDetails given user details
   * @return a {@link Boolean} valid status as true false
   */
  public boolean validateToken(final String token, final UserDetails userDetails) {
    final String username = getUsernameFromToken(token);
    return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
  }
}
