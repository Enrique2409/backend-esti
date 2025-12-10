package org.esti.backend_esti.DTO;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SystemConfigDTO {

    private Long id;

    private Boolean lockedGrades;

    private LocalDateTime lockedDate;

    private Long lockedBy;

    private String notes;
}
