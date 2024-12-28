package org.esti.backend_esti.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.*;
import org.esti.backend_esti.Form.LevelForm;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "level")
public class Level {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_level")
    private Long idLevel;

    @Column(name = "level", nullable = false)
    @Min(value = 1, message = "El nivel debe ser mayor que 0")
    private Integer level;

    public Level(final LevelForm form) {
        this.level = form.getLevel();
    }

    public void updateLevel(final LevelForm form) {
        if (form.getLevel() != null) {
            this.level = form.getLevel();
        }
    }
}
