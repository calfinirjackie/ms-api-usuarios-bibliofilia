package com.example.duoc.api.ms_api_usuarios_bibliofilia.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "USUARIO")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_USUARIO")
    private Long idUsuario;

    @Column(name = "NOMBRE", length = 100, nullable = false)
    private String nombre;

    @Column(name = "CORREO", length = 150, nullable = false)
    private String correo;

    @Column(name = "CONTRASENA", length = 255, nullable = false)
    private String contrasena;

    @Column(name = "ROL", length = 20)
    private String rol;
}

