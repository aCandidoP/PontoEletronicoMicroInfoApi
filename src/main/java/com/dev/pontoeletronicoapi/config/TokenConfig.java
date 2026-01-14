package com.dev.pontoeletronicoapi.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.dev.pontoeletronicoapi.model.Usuario;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Optional;

@Component
public class TokenConfig {
    @Value("${application.security.jwt.secret-key}")
    private String jwtSecret;


    public String generateToken(Usuario usuario){
        Algorithm algorithm = Algorithm.HMAC256(jwtSecret);

        return JWT.create()
                .withClaim("usuarioId", usuario.getId())
                .withClaim("role", usuario.getRole().name())
                .withClaim("nome", usuario.getNome())
                .withSubject(usuario.getCpf())
                .withExpiresAt(Instant.now().plusSeconds(86400))
                .withIssuedAt(Instant.now())
                .sign(algorithm);
    }


    public Optional<JWTUserData> validateToken(String token){
        try {
            Algorithm algorithm = Algorithm.HMAC256(jwtSecret);
            DecodedJWT decoded = JWT.require(algorithm).build().verify(token);

            return Optional.of(
                    JWTUserData.builder()
                            .userId(decoded.getClaim("usuarioId").asLong())
                            .cpf(decoded.getSubject())
                            .role(decoded.getClaim("role").asString())
                            .build()
            );

        } catch (JWTVerificationException exception) {
            return Optional.empty();
        }
    }

}

