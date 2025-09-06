package org.esti.backend_esti.Service;

import org.esti.backend_esti.DTO.ContentDTO;
import org.esti.backend_esti.Entity.Content;
import org.esti.backend_esti.Form.ContentForm;
import org.esti.backend_esti.Repository.ContentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.mock.web.MockMultipartFile;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ContentTest {

    @Mock
    private ContentRepository contentRepository;

    @InjectMocks
    private ContentService contentService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createContent_successful() {
        ContentForm form = new ContentForm();
        form.setCategory("Cards");
        form.setTitle("Título de prueba");
        form.setDescription("Descripción");
        form.setState(true);
        form.setLink("http://ejemplo.com");
        form.setImage(new MockMultipartFile("file", "imagen.jpg", "image/jpeg", "fake image content".getBytes()));

        when(contentRepository.countByCategoryIgnoreCaseAndDeletedAtIsNull("Cards")).thenReturn(0);
        when(contentRepository.save(any(Content.class))).thenAnswer(invocation -> invocation.getArgument(0));

        ContentDTO result = contentService.createContent(form);

        assertNotNull(result);
        assertEquals("Título de prueba", result.getTitle());
        verify(contentRepository).save(any(Content.class));
    }

    @Test
    void createContent_exceedsCategoryLimit_shouldThrowException() {
        // Arrange
        ContentForm form = new ContentForm();
        form.setCategory("Carrusel");

        when(contentRepository.countByCategoryIgnoreCaseAndDeletedAtIsNull("Carrusel")).thenReturn(10);

        // Act & Assert
        RuntimeException ex = assertThrows(RuntimeException.class, () -> contentService.createContent(form));
        assertTrue(ex.getMessage().contains("No se pueden agregar más elementos"));
    }

    @Test
    void findById_whenExists_returnsDTO() throws Exception {
        // Arrange
        Content content = new Content();
        content.setId(1L);
        content.setTitle("Prueba");

        when(contentRepository.existsById(1L)).thenReturn(true);
        when(contentRepository.findById(1L)).thenReturn(Optional.of(content));

        // Act
        ContentDTO result = contentService.findById(1L);

        // Assert
        assertNotNull(result);
        assertEquals("Prueba", result.getTitle());
    }

    @Test
    void findById_notFound_shouldThrowException() {
        // Arrange
        when(contentRepository.existsById(1L)).thenReturn(false);

        // Act & Assert
        Exception ex = assertThrows(Exception.class, () -> contentService.findById(1L));
        assertEquals("Image not found", ex.getMessage());
    }
}
