package com.dev.pontoeletronicoapi.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    private final TokenConfig tokenConfig;

    public SecurityFilter(TokenConfig tokenConfig) {
        this.tokenConfig = tokenConfig;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        // Se não tem token, segue sem autenticar
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = authHeader.replace("Bearer ", "");

        try {
            // Valida e decodifica o token
            Optional<JWTUserData> jwtUserDataOpt = tokenConfig.validateToken(token);

            if (jwtUserDataOpt.isEmpty()) {
                filterChain.doFilter(request, response);
                return;
            }

            JWTUserData jwtUserData = jwtUserDataOpt.get();


            // Extrai dados do token
            String cpf = jwtUserData.cpf();
            String role = jwtUserData.role();

            // (opcional) dados extras
            Long usuarioId = jwtUserData.userId();
            String nomeUsuario = jwtUserData.nome();

            // Cria authorities a partir da role
            List<GrantedAuthority> authorities =
                    List.of(new SimpleGrantedAuthority("ROLE_" + role));

            // Cria Authentication
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(
                            cpf,        // principal (ou um objeto seu)
                            null,
                            authorities
                    );

            // Coloca no contexto de segurança
            SecurityContextHolder.getContext().setAuthentication(authentication);

        } catch (JWTVerificationException ex) {
            // token inválido → não autentica
            SecurityContextHolder.clearContext();
        }

        filterChain.doFilter(request, response);
    }

}
