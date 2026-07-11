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
    
    private final PrivateKey privateKey;
    private final PublicKey publicKey;

    private final String algorithm;
    private final String issuer;

    private final long accessExpiresIn;
    private final long refreshExpiresIn;

    public JwtService(
            @Value("${jwt.private-key}") Resource privateKeyResource,
            @Value("${jwt.public-key}") Resource publicKeyResource,
            @Value("${jwt.algorithm}") String algorithm,
            @Value("${jwt.issuer}") String issuer,
            @Value("${jwt.access-expiration-time}") long accessExpiresIn,
            @Value("${jwt.refresh-expiration-time}") long refreshExpiresIn
    ) {
        this.algorithm = algorithm;
        this.issuer = issuer;
        this.accessExpiresIn = accessExpiresIn;
        this.refreshExpiresIn = refreshExpiresIn;

        this.privateKey = loadPrivateKey(privateKeyResource);
        this.publicKey = loadPublicKey(publicKeyResource);
    }

    public String generateToken(String subject, TokenType type) {
        Date issuedAt = new Date(System.currentTimeMillis());
        Date expirationDate = type.equals(TokenType.ACCESS) ? new Date(issuedAt.getTime() + this.accessExpiresIn) : new Date(issuedAt.getTime() + this.refreshExpiresIn);
            
        String token = Jwts.builder()
            .issuer(this.issuer)
            .subject(subject)
            .expiration(expirationDate)
            .issuedAt(new Date(System.currentTimeMillis()))
            .claim("type", type.value())
            .signWith(privateKey)
            .compact();

        return token;
    }

    public Jws<Claims> getClaims(String token, TokenType type){
        return Jwts.parser().verifyWith(publicKey).require("type", type.value()).build().parseSignedClaims(token);
    }

    public String getSubject(String token, TokenType type) {
        return getClaims(token, type).getPayload().getSubject();
    }

    public long getAccessExpiresIn() {
        return accessExpiresIn;
    }

    private PrivateKey loadPrivateKey(Resource resource) {
        try {
            byte[] bytes = getKeyBytesPrivate(resource);

            KeyFactory keyFactory = KeyFactory.getInstance(this.algorithm);

            return keyFactory.generatePrivate(new PKCS8EncodedKeySpec(bytes));
        } catch (Exception e) {
            throw new RuntimeException("Failed to load private key", e);
        }
    }
    
    private PublicKey loadPublicKey(Resource resource) {
        try {
            byte[] bytes = getKeyBytesPublic(resource);

            KeyFactory keyFactory = KeyFactory.getInstance(this.algorithm);

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
