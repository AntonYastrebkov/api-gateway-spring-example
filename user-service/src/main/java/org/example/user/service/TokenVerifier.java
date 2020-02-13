package org.example.user.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import org.springframework.stereotype.Service;

@Service
public class TokenVerifier {

    public void verify(String token) {
        try {
            Claims claims = Jwts.parser().setSigningKey("secretKey").parseClaimsJws(token).getBody();
            System.out.println(claims.toString());
//            TokenSubject subject = (TokenSubject) claims;
//            System.out.println(subject.getUserId());
        } catch (SignatureException ex) {
            System.out.println("Invalid JWT signature");
            throw new RuntimeException("Invalid JWT signature");
        } catch (MalformedJwtException ex) {
            System.out.println("Invalid JWT token");
            throw new RuntimeException("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            System.out.println("Expired JWT token");
            throw new RuntimeException("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            System.out.println("Unsupported JWT token");
            throw new RuntimeException("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            System.out.println("JWT claims string is empty.");
            throw new RuntimeException("JWT claims string is empty.");
        }
    }
}
