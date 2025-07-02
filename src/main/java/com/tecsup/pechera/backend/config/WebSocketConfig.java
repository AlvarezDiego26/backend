package com.tecsup.pechera.backend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/**
 * WebSocketConfig
 *
 * Clase de configuración para habilitar y usar WebSockets con STOMP en nuestra aplicación.
 *
 * Permite la comunicación en tiempo real entre el servidor y los clientes (como la app móvil),
 * esencial para el flujo de datos de sensores de la pechera.
 *
 * @author VitalPaw
 * @version 1.0
 * @since 2025-06-23
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    /**
     * Configura el broker de mensajes para manejar los mensajes STOMP.
     *
     * Aquí definimos cómo se enrutarán los mensajes dentro de la aplicación:
     * 1.  enableSimpleBroker("/topic"): Registra un broker simple. Mensajes a /topic
     * serán difundidos a todos los clientes suscritos (ideal para actualizaciones de sensores).
     * 2.  setApplicationDestinationPrefixes("/app"): Define el prefijo para destinos a los que
     * los clientes pueden enviar mensajes para ser procesados por controladores (@MessageMapping).
     * Ejemplo: un cliente envía a /app/registerSensor.
     *
     * @param config El MessageBrokerRegistry para configurar el broker de mensajes.
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic"); // Mensajes a /topic se difunden.
        config.setApplicationDestinationPrefixes("/app"); // Mensajes a /app van a nuestros controladores.
    }

    /**
     * Registra los endpoints STOMP que los clientes usarán para conectarse al servidor WebSocket.
     *
     * Este método es crucial para que los clientes puedan establecer una conexión WebSocket.
     *
     * @param registry El StompEndpointRegistry para registrar los endpoints.
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws")
                .setAllowedOrigins("*"); // Permite conexiones WebSocket desde CUALQUIER origen.
                                        // ¡TENER EN CUENTA! En producción, esto debería ser reemplazado
                                        // por los dominios específicos por seguridad (ejemplo: "http://localhost:8080").
    }

}