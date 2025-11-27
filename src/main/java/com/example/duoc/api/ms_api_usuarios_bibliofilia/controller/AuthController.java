package com.example.duoc.api.ms_api_usuarios_bibliofilia.controller;

import com.example.duoc.api.ms_api_usuarios_bibliofilia.entity.AuthResponse;
import com.example.duoc.api.ms_api_usuarios_bibliofilia.entity.AuthRequest;
import com.example.duoc.api.ms_api_usuarios_bibliofilia.entity.Usuario;
import com.example.duoc.api.ms_api_usuarios_bibliofilia.repository.UsuarioRepository;
import com.example.duoc.api.ms_api_usuarios_bibliofilia.service.JwtService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
@Tag(name = "Autenticación", description = "APIs para autenticación y generación de tokens JWT")
public class AuthController {
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UsuarioRepository usuarioRepository;

    public AuthController(final JwtService jwtService, final AuthenticationManager authenticationManager, final UsuarioRepository usuarioRepository){
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.usuarioRepository = usuarioRepository;
    }

    @Operation(
        summary = "Iniciar sesión",
        description = "Autentica un usuario validando sus credenciales contra la base de datos y retorna un token JWT válido junto con la información del usuario"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Autenticación exitosa, token generado",
            content = @Content(schema = @Schema(implementation = AuthResponse.class))),
        @ApiResponse(responseCode = "401", description = "Credenciales inválidas",
            content = @Content)
    })
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "Credenciales de autenticación (correo y contraseña)",
                required = true,
                content = @Content(schema = @Schema(implementation = AuthRequest.class))
            )
            @RequestBody AuthRequest request){

        // Buscar usuario por correo
        Optional<Usuario> usuarioOpt = usuarioRepository.findByCorreo(request.getUsername());

        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();

            // Validar contraseña
            if (usuario.getContrasena().equals(request.getPassword())) {
                String token = jwtService.generateToken(usuario.getCorreo());

                // Crear respuesta con token y datos del usuario
                AuthResponse response = new AuthResponse(token, usuario);
                return ResponseEntity.ok(response);
            }
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}
