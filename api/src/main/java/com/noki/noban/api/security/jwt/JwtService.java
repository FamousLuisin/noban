package com.noki.noban.api.security.jwt;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;

@Service
public class JwtService {
    
    private final PrivateKey PRIVATE_KEY;

    private final PublicKey PUBLIC_KEY;

    private final String ALGORITHM;

    private final String ISSUER;
    
    private final long EXPIRATION_TIME;

    public JwtService(
            @Value("${jwt.private-key}") Resource PRIVATE_KEY, 
            @Value("${jwt.public-key}") Resource PUBLIC_KEY,
            @Value("${jwt.algorithm}") String ALGORITHM,
            @Value("${jwt.issuer}") String ISSUER,
            @Value("${jwt.expiration-time}") long EXPIRATION_TIME
        ) {
        this.ISSUER = ISSUER;
        this.ALGORITHM = ALGORITHM;
        this.EXPIRATION_TIME = EXPIRATION_TIME;
        this.PRIVATE_KEY = this.loadPrivateKey(PRIVATE_KEY);
        this.PUBLIC_KEY = this.loadPublicKey(PUBLIC_KEY);
    }

    public String generateTokenResponse(String subject) {
        Date issuedAt = new Date(System.currentTimeMillis());
        Date expirationDate = new Date(issuedAt.getTime() + this.EXPIRATION_TIME);
            
        String token = Jwts.builder()
            .issuer(this.ISSUER)
            .subject(subject)
            .expiration(expirationDate)
            .issuedAt(new Date(System.currentTimeMillis()))
            .signWith(PRIVATE_KEY)
            .compact();

        return token;
    }

    public String getSubject(String token) {
        Jws<Claims> claims =Jwts.parser().verifyWith(PUBLIC_KEY).build().parseSignedClaims(token);
        return claims.getPayload().getSubject();
    }

    private PrivateKey loadPrivateKey(Resource resource) {
        try {
            byte[] bytes = getKeyBytesPrivate(resource);

            KeyFactory keyFactory = KeyFactory.getInstance(this.ALGORITHM);

            return keyFactory.generatePrivate(new PKCS8EncodedKeySpec(bytes));
        } catch (Exception e) {
            throw new RuntimeException("Failed to load private key", e);
        }
    }
    
    private PublicKey loadPublicKey(Resource resource) {
        try {
            byte[] bytes = getKeyBytesPublic(resource);

            KeyFactory keyFactory = KeyFactory.getInstance(this.ALGORITHM);

            return keyFactory.generatePublic(new X509EncodedKeySpec(bytes));
        } catch (Exception e) {
            throw new RuntimeException("Failed to load public key", e);
        }
    }

    private byte[] getKeyBytesPublic(Resource resource) throws IOException {
        String pem = new String(resource.getInputStream().readAllBytes(), StandardCharsets.UTF_8)
            .replace("-----BEGIN PUBLIC KEY-----", "")
            .replace("-----END PUBLIC KEY-----", "")
            .replaceAll("\\s", "");
        
        return Base64.getDecoder().decode(pem);
    }

    private byte[] getKeyBytesPrivate(Resource resource) throws IOException {
        String pem = new String(resource.getInputStream().readAllBytes(), StandardCharsets.UTF_8)
            .replace("-----BEGIN PRIVATE KEY-----", "")
            .replace("-----END PRIVATE KEY-----", "")
            .replaceAll("\\s", "");
        
        return Base64.getDecoder().decode(pem);
    }
}
