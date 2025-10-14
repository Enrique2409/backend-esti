package org.esti.backend_esti.DTO;

import lombok.Builder;
import lombok.Data;
import org.esti.backend_esti.Entity.Group;

@Data
@Builder
public class GroupDTO {

    private Long idGroup;

    private String groupName;

    private Integer grade;

    private Long periodId;

    public static GroupDTO build(final Group group) {
        return GroupDTO.builder()
                .idGroup(group.getIdGroup())
                .groupName(group.getGroupName())
                .grade(group.getGrade())
                .periodId(group.getPeriod() != null ? group.getPeriod().getIdPeriod() : null)
                .build();
    }
}
