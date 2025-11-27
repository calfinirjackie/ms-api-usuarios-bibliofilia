package com.example.duoc.api.ms_api_usuarios_bibliofilia.service;

import com.example.duoc.api.ms_api_usuarios_bibliofilia.entity.Usuario;
import com.example.duoc.api.ms_api_usuarios_bibliofilia.exception.ValidationNotFoundException;
import com.example.duoc.api.ms_api_usuarios_bibliofilia.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    /**
     * Obtener todos los usuarios
     */
    public List<Usuario> obtenerTodos() {
        return usuarioRepository.findAll();
    }

    /**
     * Obtener usuario por ID
     */
    public Usuario obtenerPorId(Long id) throws ValidationNotFoundException {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new ValidationNotFoundException("Usuario no encontrado con ID: " + id));
    }

    /**
     * Obtener usuario por correo
     */
    public Optional<Usuario> obtenerPorCorreo(String correo) {
        return usuarioRepository.findByCorreo(correo);
    }

    /**
     * Crear un nuevo usuario
     */
    public Usuario crear(Usuario usuario) throws ValidationNotFoundException {
        // Validar que el correo no esté ya registrado
        if (usuarioRepository.findByCorreo(usuario.getCorreo()).isPresent()) {
            throw new ValidationNotFoundException("El correo " + usuario.getCorreo() + " ya está registrado");
        }
        return usuarioRepository.save(usuario);
    }

    /**
     * Actualizar un usuario existente
     * Nota: El correo no es modificable y siempre mantiene el valor existente
     */
    public Usuario actualizar(Long id, Usuario usuarioActualizado) throws ValidationNotFoundException {
        Usuario usuarioExistente = obtenerPorId(id);

        // Actualizar solo los campos modificables (el correo NO es modificable)
        usuarioExistente.setNombre(usuarioActualizado.getNombre());
        // El correo se mantiene sin cambios (no es modificable)
        usuarioExistente.setContrasena(usuarioActualizado.getContrasena());
        usuarioExistente.setRol(usuarioActualizado.getRol());

        return usuarioRepository.save(usuarioExistente);
    }

    /**
     * Eliminar un usuario
     */
    public void eliminar(Long id) throws ValidationNotFoundException {
        Usuario usuario = obtenerPorId(id);
        usuarioRepository.delete(usuario);
    }
}

