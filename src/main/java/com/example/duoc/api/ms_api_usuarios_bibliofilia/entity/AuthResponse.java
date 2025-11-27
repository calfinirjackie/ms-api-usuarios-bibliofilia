package com.example.duoc.api.ms_api_usuarios_bibliofilia.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {

    private String token;
    private Usuario usuario;

    public AuthResponse(String token) {
        this.token = token;
    }
}
