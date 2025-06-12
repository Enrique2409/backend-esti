package org.esti.backend_esti.Entity;

import org.esti.backend_esti.Form.ContentForm;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@Table(name = "Content")
public class Content {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(nullable = false, length = 100)
    private String title; // Título descriptivo de la imagen

    @Column(columnDefinition = "TEXT")
    private String description; // Descripción de la imagen

    @Column(length = 50)
    private String category; // Categoría a la que pertenece la imagen

    @Column(nullable = false)
    private Boolean state = true; // Estado booleano (activo/inactivo)

    @Column(length = 255, nullable = true)
    private String imageURL;

    @Column(length = 255, nullable = true)
    private String link;


    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now(); // Fecha de creación

    @Column(nullable = false)
    private LocalDateTime updatedAt = LocalDateTime.now(); // Fecha de actualización

    private LocalDateTime deletedAt; // Fecha de eliminación lógica (nulo si no está eliminada)

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public Content(String title, String description, String category, Boolean state, String imageURL, String link) {
        this.imageURL = imageURL;
        this.title = title;
        this.description = description;
        this.category = category;
        this.state = state;
        this.link = link;
    }

    public void updateContent(final ContentForm contentForm) {
        this.setTitle(contentForm.getTitle());
        this.setDescription(contentForm.getDescription());
        this.setState(contentForm.getState());
        this.setImageURL(contentForm.getImageURL());
        this.setLink(contentForm.getLink());
    }
}
