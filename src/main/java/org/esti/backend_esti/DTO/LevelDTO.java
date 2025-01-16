package org.esti.backend_esti.DTO;


import lombok.Builder;
import lombok.Data;
import org.esti.backend_esti.Entity.Level;

@Data
@Builder
public class LevelDTO {

    private Long idlevel;

    private Integer level;

    public static LevelDTO build(final Level level) {
        return LevelDTO.builder()
                .idlevel(level.getIdLevel())
                .level(level.getLevel())
                .build();
    }
}
