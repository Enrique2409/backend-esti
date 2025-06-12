package org.esti.backend_esti.Service;

import org.esti.backend_esti.Repository.ContentRepository;
import org.esti.backend_esti.Entity.Content;
import org.esti.backend_esti.DTO.ContentDTO;
import org.esti.backend_esti.Form.ContentForm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
@Service
public class ContentService {
    private static final Map<String, Integer> CATEGORY_LIMITS = Map.of(
            "Carrusel", 10,
            "Cards", 9,
            "News", 20 // agrega más si necesitas
    );
    @Autowired
    private ContentRepository contentRepository;

    public ContentDTO createContent(final ContentForm contentForm) {
        final String category = contentForm.getCategory();

        // Validar límite de categoría
        if (CATEGORY_LIMITS.containsKey(category)) {
            int currentCount = contentRepository.countByCategoryIgnoreCaseAndDeletedAtIsNull(category);
            int maxAllowed = CATEGORY_LIMITS.get(category);
            if (currentCount >= maxAllowed) {
                throw new RuntimeException("No se pueden agregar más elementos en la categoría '" + category +
                        "'. Límite permitido: " + maxAllowed);
            }
        }

        final Content content = new Content();

        try {
            MultipartFile file = contentForm.getImage();
            if (file != null && !file.isEmpty()) {
                String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();
                Path uploadPath = Paths.get("uploads");

                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }

                Path filePath = uploadPath.resolve(filename);
                Files.write(filePath, file.getBytes());

                String fileUrl = "/uploads/" + filename;
                content.setImageURL(fileUrl);
            } else {
                content.setImageURL(contentForm.getImageURL());
            }
        } catch (Exception e) {
            throw new RuntimeException("Error al guardar la imagen", e);
        }

        content.setTitle(contentForm.getTitle());
        content.setDescription(contentForm.getDescription());
        content.setCategory(category);
        content.setState(contentForm.getState());
        content.setLink(contentForm.getLink());
        content.setCreatedAt(LocalDateTime.now());
        content.setUpdatedAt(LocalDateTime.now());

        contentRepository.save(content);
        return ContentDTO.build(content);
    }


    public void deleteImageFile(String imageUrl) {
        if (imageUrl != null && !imageUrl.isEmpty()) {
            try {
                // Suponemos que los links son como "/uploads/uuid_nombre.jpg"
                String filename = Paths.get(imageUrl).getFileName().toString();
                Path filePath = Paths.get("uploads").resolve(filename);
                Files.deleteIfExists(filePath);
            } catch (Exception e) {
                throw new RuntimeException("No se pudo eliminar la imagen anterior: " + e.getMessage(), e);
            }
        }
    }

    public ContentDTO updateContent(final Long idContent, final ContentForm contentForm) throws Exception {
        // 1. Validar si existe el contenido
        Content content = contentRepository.findById(idContent)
                .orElseThrow(() -> new RuntimeException("Contenido no encontrado con ID: " + idContent));

        // 2. Actualizar imagen si se proporcionó una nueva
        MultipartFile newFile = contentForm.getImage();
        if (newFile != null && !newFile.isEmpty()) {
            // 2.1 Eliminar la imagen anterior
            deleteImageFile(content.getImageURL());

            // 2.2 Guardar la nueva imagen
            String filename = UUID.randomUUID() + "_" + newFile.getOriginalFilename();
            Path uploadPath = Paths.get("uploads");
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            Path newFilePath = uploadPath.resolve(filename);
            Files.write(newFilePath, newFile.getBytes());

            // 2.3 Establecer nuevo link
            String newLink = "/uploads/" + filename;
            content.setImageURL(newLink);
        } else {
            // Si no hay nueva imagen, conservar el link anterior o reemplazar con el proporcionado
            if (contentForm.getImageURL() != null && !contentForm.getImageURL().isBlank()) {
                content.setImageURL(contentForm.getImageURL());
            }
        }

        // 3. Actualizar otros datos del contenido
        content.setTitle(contentForm.getTitle());
        content.setDescription(contentForm.getDescription());
        content.setCategory(contentForm.getCategory());
        content.setState(contentForm.getState());
        content.setLink(contentForm.getLink());
        // 4. Guardar cambios
        contentRepository.save(content);

        // 5. Retornar DTO
        return ContentDTO.build(content);
    }

    public ContentDTO findById(final Long idContent) throws Exception {
        validateIfContentExists(idContent);

        final Content content = contentRepository.findById(idContent)
                .orElseThrow(() -> new Exception("Content not found with ID: " + idContent));

        return ContentDTO.build(content);
    }


    public List<ContentDTO> getAllPhotos() {
        final List<Content> content = contentRepository.findByDeletedAtIsNull();
        return content.stream().map(ContentDTO::build).collect(Collectors.toList());
    }

    public List<ContentDTO> findAllActiveContent() {
        final List<Content> contents = contentRepository.findByState(true);
        List<ContentDTO> dtos = new ArrayList<>();

        for (Content content : contents) {
            ContentDTO dto = ContentDTO.build(content);
            dtos.add(dto);
        }

        return dtos;
    }

    private void validateIfContentExists(final Long idContent) throws Exception {
        if (!contentRepository.existsById(idContent)) {
            throw new Exception("Image not found");
        }
    }

    public List<ContentDTO> findByCategory(final String category) {
        final List<Content> contents = contentRepository.findByCategoryIgnoreCase(category);
        List<ContentDTO> dtos = new ArrayList<>();

        for (Content content : contents) {
            ContentDTO dto = ContentDTO.build(content);
            dtos.add(dto);
        }

        return dtos;
    }


}
