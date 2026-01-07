package com.dev.pontoeletronicoapi.controller;


import com.dev.pontoeletronicoapi.config.TokenConfig;
import com.dev.pontoeletronicoapi.dto.login.LoginRequest;
import com.dev.pontoeletronicoapi.dto.login.LoginResponse;
import com.dev.pontoeletronicoapi.dto.usuario.UsuarioCreateDTO;
import com.dev.pontoeletronicoapi.dto.usuario.UsuarioViewDTO;
import com.dev.pontoeletronicoapi.model.Usuario;
import com.dev.pontoeletronicoapi.repository.UsuarioRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final TokenConfig tokenConfig;

    public AuthController(UsuarioRepository usuarioRepository,  PasswordEncoder passwordEncoder,
                          AuthenticationManager authenticationManager, TokenConfig tokenConfig) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.tokenConfig = tokenConfig;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        UsernamePasswordAuthenticationToken usuarioAndSenha = new UsernamePasswordAuthenticationToken(loginRequest.cpf(), loginRequest.senha());
        Authentication authentication = authenticationManager.authenticate(usuarioAndSenha);
        Usuario usuario = (Usuario) authentication.getPrincipal();
        String token = tokenConfig.generateToken(usuario);

        return ResponseEntity.ok(new LoginResponse(token));

    }

    @PostMapping("/registrar")
    public ResponseEntity<UsuarioViewDTO> register(@Valid @RequestBody UsuarioCreateDTO usuarioCreateDTO) {
        Usuario novoUsuario = new Usuario();
        novoUsuario.setCpf(usuarioCreateDTO.cpf());
        novoUsuario.setSenha(passwordEncoder.encode(usuarioCreateDTO.senha()));
        novoUsuario.setNome(usuarioCreateDTO.nome());
        Usuario  usuario = usuarioRepository.save(novoUsuario);


        return ResponseEntity.status(HttpStatus.CREATED).body(new UsuarioViewDTO(usuario.getId(), usuario.getNome(), usuario.getCpf()));
    }

}
