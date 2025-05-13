package org.esti.backend_esti.Form;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serializable;

@Data
public class LevelForm implements Serializable {

    private Integer level;

}
