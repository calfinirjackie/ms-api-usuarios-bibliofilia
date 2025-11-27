package com.example.duoc.api.ms_api_usuarios_bibliofilia.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.stereotype.Service;
import java.util.Date;

@Service
public class JwtService {
    private static final String SECRET_KEY = "9fd8cb7b-9bee-40be-b76f-e563b1548b72";
    private static final long EXPIRATION_TIME = 3600000;
    private static final Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);
    public String generateToken(String username){
        return JWT.create().withSubject(username).withIssuer("libro-api").withIssuedAt(new Date()).withExpiresAt(new Date(System.currentTimeMillis()+EXPIRATION_TIME)).sign(algorithm);
    }

    public String extractUsername(String token){
        DecodedJWT decodedjwt = JWT.require(algorithm).withIssuer("libro-api").build().verify(token);
        return decodedjwt.getSubject();
    }
}
