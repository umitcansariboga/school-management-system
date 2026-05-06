package com.ucs.security.jwt;

import com.ucs.security.service.UserDetailsImpl;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Slf4j
@Component
public class JwtUtils {

    @Value("${backendapi.app.jwtExpirationMs}")
    private Long jwtExpirationMs;

    @Value("${backendapi.app.jwtSecret}")
    private String jwtSecret;

    //Helper
    private SecretKey getSignInKey() {
        return Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
    }

    public String generateJwtToken(Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        return generateTokenFromUsername(userDetails.getUsername());
    }

    private String generateTokenFromUsername(String username) {
        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date())
                .expiration(new Date(new Date().getTime() + jwtExpirationMs))
                .signWith(getSignInKey())
                .compact();
    }

    public boolean validateJwtToken(String jwtToken) {
        try {
            Jwts.parser()
                    .verifyWith(getSignInKey())
                    .build()
                    .parseSignedClaims(jwtToken);
            return true;

        } catch (SignatureException e) {
            log.error("Invalid JWT signature (Someone may have tried to tamper with the token): {}", e.getMessage());
        } catch (MalformedJwtException e) {
            log.error("malformed JWT token (Missing or invalid character found): {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            log.error("JWT token has expired (The user needs to login again): {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.error("Unsupported JWT token: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            log.error("JWT claims String is empty(The token content may be empty): {}", e.getMessage());
        }
        return false;
    }

    //EXTRACT
    public String getUsernameFromJwtToken(String token) {
        return Jwts.parser()
                .verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }
}
