package com.example.duoc.api.ms_api_usuarios_bibliofilia.controller;

import com.example.duoc.api.ms_api_usuarios_bibliofilia.entity.Usuario;
import com.example.duoc.api.ms_api_usuarios_bibliofilia.exception.ValidationNotFoundException;
import com.example.duoc.api.ms_api_usuarios_bibliofilia.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
@Tag(name = "Usuarios", description = "APIs para la gestión de usuarios")
@SecurityRequirement(name = "bearerAuth")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @Operation(
        summary = "Obtener todos los usuarios",
        description = "Retorna una lista con todos los usuarios registrados en el sistema"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de usuarios obtenida exitosamente",
            content = @Content(schema = @Schema(implementation = Usuario.class))),
        @ApiResponse(responseCode = "401", description = "No autorizado",
            content = @Content)
    })
    @GetMapping
    public ResponseEntity<List<Usuario>> obtenerTodos() {
        List<Usuario> usuarios = usuarioService.obtenerTodos();
        return ResponseEntity.ok(usuarios);
    }

    @Operation(
        summary = "Obtener usuario por ID",
        description = "Retorna un usuario específico buscado por su ID"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuario encontrado exitosamente",
            content = @Content(schema = @Schema(implementation = Usuario.class))),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado",
            content = @Content),
        @ApiResponse(responseCode = "401", description = "No autorizado",
            content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> obtenerPorId(@PathVariable Long id) throws ValidationNotFoundException {
        Usuario usuario = usuarioService.obtenerPorId(id);
        return ResponseEntity.ok(usuario);
    }

    @Operation(
        summary = "Crear un nuevo usuario",
        description = "Crea un nuevo usuario en el sistema. El correo debe ser único"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Usuario creado exitosamente",
            content = @Content(schema = @Schema(implementation = Usuario.class))),
        @ApiResponse(responseCode = "400", description = "Datos inválidos o correo ya registrado",
            content = @Content),
        @ApiResponse(responseCode = "401", description = "No autorizado",
            content = @Content)
    })
    @PostMapping
    public ResponseEntity<Usuario> crear(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "Datos del usuario a crear",
                required = true,
                content = @Content(schema = @Schema(implementation = Usuario.class))
            )
            @RequestBody Usuario usuario) throws ValidationNotFoundException {
        Usuario nuevoUsuario = usuarioService.crear(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoUsuario);
    }

    @Operation(
        summary = "Actualizar un usuario existente",
        description = "Actualiza los datos de un usuario existente. NOTA: El correo NO es modificable y siempre se mantiene el correo original"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuario actualizado exitosamente",
            content = @Content(schema = @Schema(implementation = Usuario.class))),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado",
            content = @Content),
        @ApiResponse(responseCode = "400", description = "Datos inválidos",
            content = @Content),
        @ApiResponse(responseCode = "401", description = "No autorizado",
            content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<Usuario> actualizar(
            @PathVariable Long id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "Datos actualizados del usuario (el correo no es modificable)",
                required = true,
                content = @Content(schema = @Schema(implementation = Usuario.class))
            )
            @RequestBody Usuario usuario) throws ValidationNotFoundException {
        Usuario usuarioActualizado = usuarioService.actualizar(id, usuario);
        return ResponseEntity.ok(usuarioActualizado);
    }

    @Operation(
        summary = "Eliminar un usuario",
        description = "Elimina un usuario del sistema por su ID"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Usuario eliminado exitosamente",
            content = @Content),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado",
            content = @Content),
        @ApiResponse(responseCode = "401", description = "No autorizado",
            content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) throws ValidationNotFoundException {
        usuarioService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}

