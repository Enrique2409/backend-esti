package org.esti.backend_esti.Form;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

@Data
public class ContentForm implements Serializable {

    @NotBlank(message = "{title.required}")
    @Size(max = 100, message = "{title.max.length}")
    private String title;

    @Size(max = 500, message = "{description.max.length}")
    private String description;

    @Size(max = 50, message = "{category.max.length}")
    private String category;

    private Boolean state;

    @Size(max = 255, message = "{link.max.length}")
    private String imageURL;

    @Size(max = 255, message = "{link.max.length}")
    private String link;

    private MultipartFile image;
}