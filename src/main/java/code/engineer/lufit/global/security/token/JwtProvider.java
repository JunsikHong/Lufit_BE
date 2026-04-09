package code.engineer.lufit.global.security.token;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Slf4j
@Component
public class JwtProvider {

    private final SecretKey key;
    private final long accessTokenExpiry;
    private final long refreshTokenExpiry;

    public JwtProvider(
            @Value("${jwt.secret}") String key, 
            @Value("${jwt.access-token-expiry}") long accessTokenExpiry, 
            @Value("${jwt.refresh-token-expiry}") long refreshTokenExpiry) {
        this.key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(key));
        this.accessTokenExpiry = accessTokenExpiry;
        this.refreshTokenExpiry = refreshTokenExpiry;
    }
    
    public String generateAccessToken(Long userId, String userType) {
        return Jwts.builder()
                .subject(String.valueOf(userId))
                .claim("type", userType)
                .claim("tokenType", "ACCESS")
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + accessTokenExpiry))
                .signWith(key)
                .compact();
    }
    
    public String generateRefreshToken(Long userId) {
        return Jwts.builder()
                .subject(String.valueOf(userId))
                .claim("tokenType", "REFRESH")
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + refreshTokenExpiry))
                .signWith(key)
                .compact();
    }
    
    public Claims parseToken(String token) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
    
    public Long getUserId(String token) {
        return Long.valueOf(parseToken(token).getSubject());
    }
    
    public boolean validateToken(String token) {
        try {
            parseToken(token);
            return true;
        } catch (ExpiredJwtException e) {
            log.debug("만료된 토큰");
        } catch (JwtException | IllegalArgumentException e) {
            log.debug("유효하지 않은 토큰");
        }
        return false;
    }
}
