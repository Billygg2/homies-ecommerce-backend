package com.yavirac.homies.Seguridad.Demo;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Tag(name = "Pruebas de Autentificación")
public class DemoController {
    
    @PostMapping(value = "demo")
    @Operation(summary = "Mostrar mensaje cuando el usuario ingrese (Backend)")
    public String welcome()
    {
        return "Bienvenido a homies";
    }
}
