package com.proyecto.comparadorProyecto.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Data
public class LoginRequest {
    private String nombre;
    private String contrasena;
}
