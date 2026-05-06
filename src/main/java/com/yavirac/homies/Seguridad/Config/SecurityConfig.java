package com.yavirac.homies.Seguridad.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.yavirac.homies.Seguridad.Jwt.JwtAuthenticationFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final AuthenticationProvider authProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(authRequest -> authRequest
                .requestMatchers("/auth/register").permitAll()
                .requestMatchers("/auth/registerAdmin").permitAll()
                .requestMatchers("/main").permitAll()
                .requestMatchers("/auth/login").permitAll()
                .requestMatchers("/roles/**").permitAll()
                .requestMatchers("/user/**").permitAll()
                .requestMatchers("/productos/**").permitAll() // Endpoint
                .requestMatchers("/tallaProducto/**").permitAll() // Endpoint
                .requestMatchers("/proveedor/**").permitAll() // Endpoint
                .requestMatchers("/facProveedor/**").permitAll() // Endpoint
                .requestMatchers("/pedido/**").permitAll() // Endpoint
                .requestMatchers("/pedido/user/**").permitAll() // Endpoint
                .requestMatchers("/detallePedido/**").permitAll() // Endpoint
                .requestMatchers("/catalogo/**").permitAll() // Endpoint
                .requestMatchers("/v3/api-docs/**", "/swagger-ui.html", "/swagger-ui/**").permitAll()
                .anyRequest().authenticated())
            .sessionManagement(sessionManager -> sessionManager
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authenticationProvider(authProvider)
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
            .build();
    }

}