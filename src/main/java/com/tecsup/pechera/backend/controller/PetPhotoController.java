package com.tecsup.pechera.backend.controller;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

/**
 * PetPhotoController
 *
 * Controlador REST para gestionar la subida y descarga de fotos de mascotas.
 * Es la API para manejar las imágenes de las pecheras.
 *
 * @author VitalPaw
 * @version 1.0
 * @since 2025-06-23
 */
@RestController
@RequestMapping("/api/pets")
public class PetPhotoController {

    // Directorio donde se guardan las fotos de las mascotas.
    private final String uploadDir = System.getProperty("user.dir") + "/uploads/pets/";

    /**
     * Sube una foto de mascota al servidor.
     * Guarda el archivo y devuelve su nombre.
     *
     * @param file La imagen a subir.
     * @return Nombre del archivo o un error.
     */
    @PostMapping("/photo/upload")
    public ResponseEntity<Map<String, String>> uploadPhoto(@RequestParam("file") MultipartFile file) {
        try {
            String filename = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            File destinationFile = new File(uploadDir + filename);
            destinationFile.getParentFile().mkdirs(); // Crea carpetas si no existen
            file.transferTo(destinationFile); // Guarda el archivo

            Map<String, String> response = new HashMap<>();
            response.put("filename", filename);
            return ResponseEntity.ok(response);
        } catch (IOException e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Error al subir: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    /**
     * Descarga una foto de mascota por su nombre.
     * Proporciona el archivo de imagen solicitado.
     *
     * @param filename El nombre del archivo de la foto.
     * @return La imagen como recurso o un error (404 si no existe).
     */
    @GetMapping("/photo/{filename:.+}")
    public ResponseEntity<Resource> getPhoto(@PathVariable String filename) {
        try {
            Path filePath = Paths.get(uploadDir).resolve(filename).normalize();
            Resource resource = new UrlResource(filePath.toUri());

            if (!resource.exists()) {
                return ResponseEntity.notFound().build(); // Archivo no encontrado
            }

            String contentType = Files.probeContentType(filePath); // Detecta tipo de archivo (ej. image/jpeg)
            if (contentType == null) {
                contentType = "application/octet-stream"; // Tipo genérico si no se detecta
            }

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_TYPE, contentType)
                    .body(resource);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // Error al leer el archivo
        }
    }

}