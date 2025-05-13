package org.esti.backend_esti.Form;

import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import org.esti.backend_esti.Entity.*;

import java.io.Serializable;

@Data
@Builder
public class ClassForm implements Serializable {

    private Level level;

    private Group group;

    private Student student;

    private Subject subject;


} 