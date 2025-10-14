package org.esti.backend_esti.Controller;

import jakarta.validation.Valid;
import org.esti.backend_esti.DTO.GroupDTO;
import org.esti.backend_esti.Entity.Group;
import org.esti.backend_esti.Form.GroupForm;
import org.esti.backend_esti.Service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/esti/group")
public class GroupController {

    private final GroupService groupService;

    @Autowired
    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @PostMapping("/create-group")
    public ResponseEntity<GroupDTO> createGroup(@RequestBody @Valid GroupForm form) throws Exception {
        GroupDTO groupDTO = groupService.createGroup(form);
        return ResponseEntity.ok(groupDTO);
    }

    @PatchMapping("/{groupId}")
    public ResponseEntity<GroupDTO> updateGroup(@RequestBody @Valid GroupForm form, @PathVariable("groupId") final Long groupId) throws Exception {
        GroupDTO groupDTO = groupService.updateGroup(form, groupId);
        return ResponseEntity.ok(groupDTO);
    }

    @DeleteMapping("/{groupId}/hard")
    public ResponseEntity<Void> deleteGroup(@PathVariable("groupId") final Long groupId) throws Exception {
        groupService.deleteGroup(groupId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{groupId}")
    public ResponseEntity<Void> deleteGroupLogically(@PathVariable Long groupId) throws Exception {
        groupService.deleteGroupLogically(groupId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{groupId}")
    public ResponseEntity<GroupDTO> findById(@PathVariable("groupId") final Long groupId) throws Exception {
        GroupDTO groupDTO = groupService.findById(groupId);
        return ResponseEntity.ok(groupDTO);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<GroupDTO>> getAllGroups() {
        List<GroupDTO> groupsDTO = groupService.getAllGroups();
        return ResponseEntity.ok(groupsDTO);
    }

    @GetMapping("/active")
    public ResponseEntity<List<GroupDTO>> getAllActiveGroups() {
        List<Group> activeGroups = groupService.getAllActiveGroups();
        List<GroupDTO> activeGroupDTOs = activeGroups.stream().map(GroupDTO::build).toList();
        return ResponseEntity.ok(activeGroupDTOs);
    }
}
