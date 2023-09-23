package org.example.services;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.example.entities.user.UserEntity;
import org.example.entities.user.UserRoleEntity;
import org.example.repositories.UserRoleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.List;

@Service
public class JwtService {
    private final UserRoleRepo userRoleRepo;

    // A key to encrypt a token
    private static final String jwtSecretString = "404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970";
    private static final byte[] jwtSecret = Decoders.BASE64.decode(jwtSecretString);

    // Owner of the token. Write your own domain later
    private static final String jwtIssuer = "step.io";

    @Autowired
    public JwtService(UserRoleRepo userRoleRepo) {
        this.userRoleRepo = userRoleRepo;
    }

    // Method to generate an access token for a specific user
    public String generateAccessToken(UserEntity user) {
        List<UserRoleEntity> roles = userRoleRepo.findByUser(user);
        return Jwts.builder()
                .setSubject(user.getId() + "," + user.getUsername())
                .claim("email", user.getUsername())
                .claim("roles",
                        roles.stream()
                                .map(role -> role.getRole().getName())
                                .toArray(String[]::new)
                )
                .setIssuer(jwtIssuer)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 7 * 24 * 60 * 60 * 1000))
                .signWith(signInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Key signInKey() {
        return Keys.hmacShaKeyFor(jwtSecret);
    }

    // Get the user ID from the token
    public String getUserId(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(jwtSecret)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject().split(",")[0];
    }

    // Get the username from the token
    public String getUsername(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(jwtSecret)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject().split(",")[1];
    }

    // Get the expiration date of the token
    public Date getExpirationDate(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(jwtSecret)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getExpiration();
    }

    // Validate whether our token is valid and was issued by our server
    public boolean validate(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(jwtSecret).build().parseClaimsJws(token);
            return true;
        } catch (MalformedJwtException ex) {
            System.out.println("Invalid JWT token - " + ex.getMessage());
        } catch (ExpiredJwtException ex) {
            System.out.println("Expired JWT token - " + ex.getMessage());
        } catch (UnsupportedJwtException ex) {
            System.out.println("Unsupported JWT token - " + ex.getMessage());
        } catch (IllegalArgumentException ex) {
            System.out.println("JWT claims string is empty - " + ex.getMessage());
        } catch (Exception ex) {
            System.out.println("Invalid JWT signature - " + ex.getMessage());
        }
        return false;
    }
}
