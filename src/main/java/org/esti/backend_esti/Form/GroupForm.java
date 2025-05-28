package org.esti.backend_esti.Form;

import lombok.Data;

import java.io.Serializable;

@Data
public class GroupForm implements Serializable {

    private String groupName;

    private Integer grade;

    private Long periodId;
}
