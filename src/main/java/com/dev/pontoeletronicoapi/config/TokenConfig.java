package com.dev.pontoeletronicoapi.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.dev.pontoeletronicoapi.model.Usuario;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class TokenConfig {
    Dotenv dotenv  = Dotenv.load();
    private String jwt_secret = dotenv.get("JWT_SECRET");


    public String generateToken(Usuario usuario){
        Algorithm algorithm = Algorithm.HMAC256(jwt_secret);

        return JWT.create()
                .withClaim("usuarioId", usuario.getId())
                .withSubject(usuario.getCpf())
                .withExpiresAt(Instant.now().plusSeconds(86400))
                .withIssuedAt(Instant.now())
                .sign(algorithm);
    }


}
