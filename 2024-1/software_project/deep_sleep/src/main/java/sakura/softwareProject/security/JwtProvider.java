package sakura.softwareProject.security;

import io.jsonwebtoken.*;

import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;
import sakura.softwareProject.domain.Member;

import java.security.Key;
import java.util.Date;

@Component
public class JwtProvider {
    private final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    public String createToken(Member member) {
        Date now = new Date(System.currentTimeMillis());
        // long expiration = 1000 * 60 * 60L;
        return Jwts.builder().setHeaderParam("typ", "JWT")
                .setIssuedAt(now)
                // .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .setSubject(member.getId().toString())
                .signWith(key)
                .compact();
    }

    public String getId(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token) //토큰 파싱
                .getBody();
        return claims.getSubject();
    }

    public boolean validateToken(String jwt) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(jwt) //해독된 토큰 파싱
                    .getBody();
            return true;
        } catch (Exception e) {
            return false; // malformed 예외 처리는 TBD
        }
    }
}
