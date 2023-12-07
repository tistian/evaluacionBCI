package com.api.evaluacion.utils;

import com.api.evaluacion.rest.response.ErrorException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;

public class TokenGenerator {
    private Key generatedKey;
    private static TokenGenerator tokenGenerator;

    private TokenGenerator() {
        this.generatedKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    }

    public static TokenGenerator getInstance() {
        if (tokenGenerator == null) {
            tokenGenerator = new TokenGenerator();

        }
        return tokenGenerator;
    }

    public String generateToken(String email, String password) {
        String token = null;

        long now = System.currentTimeMillis();
        long expiracion = now + 3600000;

        token = Jwts.builder()
                .setSubject(email)
                .claim("password", password)
                .claim("email", email)
                .setIssuedAt(new Date(now))
                .setExpiration(new Date(expiracion))
                .signWith(this.generatedKey)
                .compact();

        return token;

    }

    public String getEmailFromToken(String token) throws ErrorException {
        String email = null;
        try {
            if (generatedKey == null) throw new ErrorException(230, "No se ha creado Key para generacion de token");
            Jws<Claims> jws = Jwts.parserBuilder().setSigningKey(generatedKey).build().parseClaimsJws(token);
            Claims claims = jws.getBody();
            email = claims.get("email", String.class);
        } catch (ErrorException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new ErrorException(160, "Token No Valido");
        }
        return email;
    }

}
