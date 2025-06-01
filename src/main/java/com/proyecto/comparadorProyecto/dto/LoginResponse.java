package com.proyecto.comparadorProyecto.dto;

import lombok.*;

@Data
@AllArgsConstructor
@Getter
@Setter
public class LoginResponse {
    private String token;
    private boolean esAdmin;
}
