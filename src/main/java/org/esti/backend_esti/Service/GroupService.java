package org.esti.backend_esti.Service;


import org.esti.backend_esti.DTO.GroupDTO;
import org.esti.backend_esti.DTO.LevelDTO;
import org.esti.backend_esti.Entity.Group;
import org.esti.backend_esti.Entity.Level;
import org.esti.backend_esti.Form.GroupForm;
import org.esti.backend_esti.Form.LevelForm;
import org.esti.backend_esti.Repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class GroupService {

    @Autowired
    private GroupRepository groupRepository;

    public GroupService(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    public GroupDTO createGroup(final GroupForm form) {
        final Group group = new Group(form);
        group.setGroupName(form.getGroupName());
        groupRepository.save(group);
        return GroupDTO.build(group);
    }

    public GroupDTO updateGroup(final GroupForm form, Long idGroup) throws Exception {
        validateIfGroupExists(idGroup);
        final Group group = groupRepository.findById(idGroup).get();
        group.updateGroup(form);
        groupRepository.save(group);
        return GroupDTO.build(group);
    }

    public void deleteGroup(final Long idGroup) throws Exception {
        validateIfGroupExists(idGroup);
        groupRepository.deleteById(idGroup);
    }

    public GroupDTO findById(Long idGroup) throws Exception {
        validateIfGroupExists(idGroup);
        final Group group = groupRepository.findById(idGroup).orElseThrow(() ->
                new Exception("Group not found with id: " + idGroup)
        );
        return GroupDTO.build(group);
    }

    public List<GroupDTO> getAllGroups()throws Exception {
        final List<Group> groups = groupRepository.findAll();
        return groups.stream().map(GroupDTO::build).toList();
    }

    public List<Group> getAllActiveGroups() {
        return groupRepository.findAllActive();
    }

    public void deleteGroupLogically(Long idGroup) throws Exception {
        Group existingGroup = groupRepository.findById(idGroup).orElseThrow(() ->
                new Exception("Group not found with id: " + idGroup)
        );
        existingGroup.markAsDeleted();
        groupRepository.save(existingGroup);
    }

    public void validateIfGroupExists(final Long idGroup) throws Exception {
        if (!groupRepository.existsById(idGroup)) {
            throw new Exception("Service not found");
        }
    }
}
