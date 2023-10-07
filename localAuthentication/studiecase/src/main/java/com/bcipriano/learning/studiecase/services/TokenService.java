package com.bcipriano.learning.studiecase.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.bcipriano.learning.studiecase.domain.Token;
import com.bcipriano.learning.studiecase.domain.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TokenService {
    @Value("${security.jwt.token.secret-key}")
    private String secret;

    @Value("${security.jwt.token.expire-length}")
    private long validityInMilliseconds;

    public Token createAccessToken(User user) {
        Date now = new Date();
        Date validity = new Date(now.getTime() + validityInMilliseconds);
        String accessToken = generateToken(user.getLogin(), now, validity);
        return new Token(user.getLogin(), true, user.getFirstName(), user.getLastName(), user.getRole(),  now, validity, accessToken);
    }

    public String generateToken(String login, Date now, Date validity){
        try{
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.create()
                    .withIssuer("auth-api")
                    .withSubject(login)
                    .withIssuedAt(now)
                    .withExpiresAt(validity)
                    .sign(algorithm);
        } catch (JWTCreationException exception) {
            throw new RuntimeException("Error while generating token", exception);
        }
    }

    public String validateToken(String token){
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer("auth-api")
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException exception){
            throw new IllegalArgumentException("Invalid token");
        }
    }

}