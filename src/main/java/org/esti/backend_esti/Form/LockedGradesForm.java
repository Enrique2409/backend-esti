package org.esti.backend_esti.Form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LockedGradesForm {

    private Boolean lock;
    private String notes;
}
