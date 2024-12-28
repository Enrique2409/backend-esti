package org.esti.backend_esti.Form;

import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serializable;

@Data
public class LevelForm implements Serializable {
    @Size(max = 100, message = "{level.right.lenght}")
    private Integer level;
}
