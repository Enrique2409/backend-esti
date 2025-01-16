package org.esti.backend_esti.DTO;


import lombok.Builder;
import lombok.Data;
import org.esti.backend_esti.Entity.Group;

@Data
@Builder
public class GroupDTO {

    private Long idGroup;

    private String groupName;

    public static GroupDTO build(final Group group) {
        return GroupDTO.builder()
                .idGroup(group.getIdGroup())
                .groupName(group.getGroupName())
                .build();
    }
}
