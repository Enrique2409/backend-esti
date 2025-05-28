package org.esti.backend_esti.Form;

import lombok.Data;

import java.io.Serializable;

@Data
public class PeriodForm implements Serializable {

    private String cve;

    private String description;

}
