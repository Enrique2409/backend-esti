package org.esti.backend_esti.Service;

import org.esti.backend_esti.DTO.GroupDTO;
import org.esti.backend_esti.Entity.Group;
import org.esti.backend_esti.Entity.Period;
import org.esti.backend_esti.Form.GroupForm;
import org.esti.backend_esti.Repository.GroupRepository;
import org.esti.backend_esti.Repository.PeriodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupService {

    private final GroupRepository groupRepository;
    private final PeriodRepository periodRepository;

    @Autowired
    public GroupService(GroupRepository groupRepository, PeriodRepository periodRepository) {
        this.groupRepository = groupRepository;
        this.periodRepository = periodRepository;
    }

    public GroupDTO createGroup(final GroupForm form) throws Exception {
        Period period = periodRepository.findById(form.getPeriodId())
                .orElseThrow(() -> new Exception("Period not found with id: " + form.getPeriodId()));

        final Group group = new Group(form, period);
        groupRepository.save(group);
        return GroupDTO.build(group);
    }

    public GroupDTO updateGroup(final GroupForm form, Long idGroup) throws Exception {
        validateIfGroupExists(idGroup);
        final Group group = groupRepository.findById(idGroup).get();

        Period period = periodRepository.findById(form.getPeriodId())
                .orElseThrow(() -> new Exception("Period not found with id: " + form.getPeriodId()));

        group.updateGroup(form, period);
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

    public List<GroupDTO> getAllGroups() {
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
            throw new Exception("Group not found with id: " + idGroup);
        }
    }
}
