package org.esti.backend_esti.Controller;

import jakarta.validation.Valid;
import org.esti.backend_esti.DTO.ContentDTO;
import org.esti.backend_esti.Form.ContentForm;
import org.esti.backend_esti.Service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/esti/content")
public class ContentController {

    @Autowired
    private ContentService contentService;

 /*   @PostMapping("/")
    public ResponseEntity createContent(@RequestBody @Valid ContentForm form) {
        ContentDTO contentDTO = contentService.createContent(form);
        return ResponseEntity.ok().body(contentDTO);
    }*/


    @PostMapping("/")
    public ResponseEntity<?> createContent(@ModelAttribute @Valid ContentForm form) {
        try {
            ContentDTO contentDTO = contentService.createContent(form);
            return ResponseEntity.ok(contentDTO);
        } catch (RuntimeException e) {
            // Devuelve el mensaje de error con código 400 (Bad Request)
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            // Para errores inesperados, devuelve 500 (Internal Server Error)
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Error inesperado: " + e.getMessage()));
        }
    }/*
    @PostMapping("/")
    public ResponseEntity<ContentDTO> createContent(@ModelAttribute @Valid ContentForm form) {
        ContentDTO contentDTO = contentService.createContent(form);
        return ResponseEntity.ok().body(contentDTO);
    }*/

    @PutMapping("/update/{id}")
    public ResponseEntity<ContentDTO> updateContent(@PathVariable Long id, @ModelAttribute ContentForm contentForm) {
        try {
            ContentDTO content = contentService.updateContent(id, contentForm);
            return ResponseEntity.ok(content);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContentDTO> getContentById(@PathVariable Long id) {
        try {
            ContentDTO content = contentService.findById(id);
            return ResponseEntity.ok(content);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<ContentDTO>> getAllContents() {
        return ResponseEntity.ok(contentService.getAllPhotos());
    }

    @GetMapping("/active")
    public ResponseEntity<List<ContentDTO>> getAllActiveContents() {
        return ResponseEntity.ok(contentService.findAllActiveContent());
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<ContentDTO>> getContentByCategory(@PathVariable String category) {
        return ResponseEntity.ok(contentService.findByCategory(category));
    }
}
