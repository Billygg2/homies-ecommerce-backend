package com.yavirac.homies.Seguridad.Auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;
import java.util.Set;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {
    String username;
    String password;
    String nombres;
    String telefono;
    Date fechaNacimiento;
    LocalDate fechaRegistro;
    String recaptchaToken;
    private Set<String> roles;
}
