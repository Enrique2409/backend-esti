package org.esti.backend_esti.DTO;

import org.esti.backend_esti.Entity.Content;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContentDTO
{
    private Long id;
    private String title;
    private String description;
    private String category;
    private Boolean state;
    private String imageURL;
    private String link;

    public static ContentDTO build(final Content content) {
        return ContentDTO.builder()
                .id(content.getId())
                .title(content.getTitle())
                .description(content.getDescription())
                .category(content.getCategory())
                .state(content.getState())
                .imageURL(content.getImageURL())
                .link(content.getLink())
                .build();
    }
}
