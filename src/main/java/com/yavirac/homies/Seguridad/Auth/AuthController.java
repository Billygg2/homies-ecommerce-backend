package com.yavirac.homies.Seguridad.Auth;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Controlador del Auth")
public class AuthController {

    private final AuthService authService;
    private final RecaptchaService reCaptchaService;

    @PostMapping(value = "login")
    @Operation(summary = "Autenticación de los usuarios (Usuario y Administrador)")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        if (!reCaptchaService.validateCaptcha(request.getRecaptchaToken())) {
            return ResponseEntity
                .badRequest()
                .body(new HashMap<String, String>() {{
                    put("message", "Invalid reCAPTCHA");
                }});
        }
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping(value = "register")
    @Operation(summary = "Registrar un usuario")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {
        if (!reCaptchaService.validateCaptcha(request.getRecaptchaToken())) {
            return ResponseEntity
                .badRequest()
                .body(AuthResponse.builder()
                    .token(null)
                    .build());
        }
        return ResponseEntity.ok(authService.register(request, null));
    }

    @PostMapping(value = "registerAdmin")
    @Operation(summary = "Registrar un administrador")
    public ResponseEntity<AuthResponse> registerAdmin(@RequestBody RegisterRequest request) {
        // Asigna el rol de administrador
        Set<String> roles = new HashSet<>();
        roles.add("ADMIN");
        return ResponseEntity.ok(authService.register(request, roles));
    }

}