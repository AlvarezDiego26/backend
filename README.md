
# Guía de Ejecución del Proyecto Pechera Inteligente (Backend)

Este documento te guiará sobre cómo poner en marcha el backend de la aplicación "VitalPaw".

-----

## 1\. Requisitos Previos

Antes de empezar, asegúrate de tener instalado lo siguiente:

  * **Java Development Kit (JDK) 17 o superior:** Necesario para compilar y ejecutar la aplicación Spring Boot.
  * **Gradle:** Para gestionar las dependencias del proyecto (este proyecto usa Gradle, ver `build.gradle.kts`).
  * **MariaDB o MySQL:** Una base de datos relacional para almacenar los datos de la aplicación.
  * **Cliente Git:** Para clonar el repositorio del proyecto.

-----

## 2\. Configuración de la Base de Datos

El backend necesita conectarse a una base de datos. Actualmente, está configurado para MariaDB.

1.  **Crear la Base de Datos:**
    Asegúrate de tener una base de datos llamada `vitalpawdb` y un usuario `vitalpaw` con la contraseña `relojde7TECSUP` (o cámbialos por tus propias credenciales seguras).

    ```sql
    CREATE DATABASE vitalpawdb;
    CREATE USER 'vitalpaw'@'%' IDENTIFIED BY 'relojde7TECSUP';
    GRANT ALL PRIVILEGES ON vitalpawdb.* TO 'vitalpaw'@'%';
    FLUSH PRIVILEGES;
    ```

2.  **Configurar `application.properties`:**
    El archivo `src/main/resources/application.properties` contiene los detalles de conexión. Si tu base de datos no está en `vitalpaw.tecsup.xyz` o tienes otras credenciales, **debes actualizar estas líneas**:

    ```properties
    spring.datasource.url=jdbc:mariadb://[TU_IP_O_HOST_DE_DB]:3306/vitalpawdb
    spring.datasource.username=vitalpaw
    spring.datasource.password=relojde7TECSUP
    ```

    **Importante:** La configuración `spring.jpa.hibernate.ddl-auto=none` significa que el backend **no creará ni modificará automáticamente** el esquema de la base de datos. Debes asegurarte de que las tablas existan o crearlas manualmente según los modelos (`SensorData`, `Pet`, etc.).

-----

## 3\. Ejecución del Backend

Puedes ejecutar la aplicación de varias maneras:

  * **Desde tu IDE (IntelliJ IDEA, Eclipse):**
    Abre el proyecto, navega a la clase `src/main/java/com/tecsup/pechera.backend/BackendApplication.java` y ejecuta el método `main`.

  * **Desde la línea de comandos con Gradle:**
    Navega a la raíz del proyecto (`marielahuanca2-pecheraapp3/backend`) y ejecuta:

    ```bash
    ./gradlew bootRun
    ```

    (En Windows, usa `gradlew bootRun`)

  * **Compilar y ejecutar el JAR:**
    Primero compila el proyecto:

    ```bash
    ./gradlew build
    ```

    Luego, ejecuta el archivo JAR generado (estará en `build/libs/`):

    ```bash
    java -jar build/libs/pechera-inteligente-backend-0.0.1-SNAPSHOT.jar
    ```

    (El nombre del JAR puede variar ligeramente).

-----

## 4\. Configuración de Red e IPs (¡Crucial\!)

Para que tu aplicación móvil y otros clientes puedan comunicarse con este backend, **necesitan la IP correcta de tu máquina o servidor**.

1.  **Obtener tu IP Local:**

      * **Windows:** Abre el Símbolo del sistema y escribe `ipconfig`. Busca tu dirección IPv4.
      * **macOS/Linux:** Abre la Terminal y escribe `ifconfig` o `ip a`. Busca la IP de tu interfaz de red activa (ej. `eth0` o `wlan0`).

2.  **Configurar en el Cliente (App Móvil):**
    Una vez que tengas tu IP local (o la IP pública de tu servidor si lo despliegas), deberás **actualizar la URL base de tu cliente (aplicación móvil)** para que apunte a esta IP.

      * **Ejemplo para el cliente Android/Kotlin:**
        En tu proyecto Android (`pecheraapp/app/src/main/java/com/example/pecherainteligenteapp/data/network/`), busca archivos como `RetrofitClient.kt`, `RetrofitClientImages.kt` o donde definas constantes de URL:

        ```kotlin
        // Archivo: RetrofitClient.kt
        object RetrofitClient {
            // DEBES CAMBIAR ESTA IP A LA IP LOCAL DE TU COMPUTADORA O A LA IP DEL SERVIDOR.
            // Ejemplo: "http://192.168.1.100:8080/api/"
            private const val BASE_URL = "http://[TU_IP]:8080/api/"

            // ... (resto del código)
        }

        // Archivo: RetrofitClientImages.kt
        object RetrofitClientImages {
            // DEBES CAMBIAR ESTA IP A LA IP LOCAL DE TU COMPUTADORA O A LA IP DEL SERVIDOR.
            // Ejemplo: "http://192.168.1.100:8080/api/pets/"
            private const val BASE_URL_IMAGES = "http://[TU_IP]:8080/api/pets/"
            // ...
        }

        // Archivo: WebSocketClientStomp.kt (para WebSockets)
        // DEBES CAMBIAR ESTA IP A LA IP LOCAL DE TU COMPUTADORA O A LA IP DEL SERVIDOR.
        // Ejemplo: "ws://192.168.1.100:8080/ws"
        private const val WS_URL = "ws://[TU_IP]:8080/ws"
        // ...
        ```

      * **`setAllowedOrigins("*")` en `WebSocketConfig` (¡Recordatorio de Seguridad\!):**
        En tu backend (`WebSocketConfig.java`), la línea `registry.addEndpoint("/ws").setAllowedOrigins("*");` permite conexiones WebSocket desde *cualquier* origen. Para **producción**, es vital que cambies `"*"` a las URLs específicas de donde tus clientes se conectarán (ej. `"http://tu-dominio-app-movil.com"`) para evitar problemas de seguridad.

-----

## 5\. Acceso a la API (Endpoints)

Una vez que el backend esté corriendo, podrás acceder a sus endpoints. Aquí algunos ejemplos basados en tu estructura:

  * **Subir foto de mascota:** `POST http://[TU_IP]:8080/api/pets/photo/upload`
  * **Obtener foto de mascota:** `GET http://[TU_IP]:8080/api/pets/photo/{filename}`
  * **Enviar datos de sensor:** `POST http://[TU_IP]:8080/api/sensores`
  * **Conexión WebSocket para sensores:** `ws://[TU_IP]:8080/ws` (luego, el cliente se suscribiría a `/topic/sensores`)

-----