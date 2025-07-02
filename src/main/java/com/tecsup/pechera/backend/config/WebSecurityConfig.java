package com.tecsup.pechera.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

/**
 * WebSecurityConfig
 *
 * Configuración de la **seguridad web** para la aplicación Spring Boot.
 *
 * Define cómo Spring Security maneja las peticiones HTTP y las reglas de acceso.
 *
 * @author VitalPaw
 * @version 1.0
 * @since 2025-06-23
 */
@Configuration
public class WebSecurityConfig {

    /**
     * Define la cadena de filtros de seguridad HTTP.
     *
     * Este bean es el corazón de la seguridad, controlando autenticación y autorización.
     *
     * Configuración actual:
     * 1.  CSRF Deshabilitado:Común para APIs REST sin estado (usando tokens como JWT).
     * 2.  Autorización:**
     * `/ws/`: Acceso sin autenticación (esencial para WebSockets).
     * `anyRequest()`: Todo lo demás también permite acceso sin autenticación.
     *
     * ¡IMPORTANTE! `anyRequest().permitAll()` es solo para desarrollo.
     * En producción, ¡modifica esto para proteger tu API!.
     *
     * @param http Objeto para configurar la seguridad HTTP.
     * @return {@link SecurityFilterChain} configurado.
     * @throws Exception Si falla la configuración de seguridad.
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf().disable() // Deshabilita protección CSRF
            .authorizeHttpRequests(authz -> authz
                .requestMatchers("/ws/**").permitAll() // Rutas de WebSocket abiertas
                .anyRequest().permitAll() // TODAS las demás rutas abiertas (¡cambiar para producción!)
            );
        return http.build();
    }
}