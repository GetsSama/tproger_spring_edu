package edu.zhuravlev.tproger_spring.clients;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service
public class DefaultTokenService implements TokenService{

    @Value("${auth.jwt.secret}")
    private String secreteKey;

    @Override
    public String generateToken(String clientId) {
        Algorithm algorithm = Algorithm.HMAC256(secreteKey);

        Instant now = Instant.now();
        Instant exp = now.plus(5, ChronoUnit.MINUTES);

        return JWT.create()
                .withIssuer("auth-service")
                .withAudience("bookstore")
                .withSubject(clientId)
                .withIssuedAt(Date.from(now))
                .withExpiresAt(Date.from(exp))
                .sign(algorithm);
    }
}
