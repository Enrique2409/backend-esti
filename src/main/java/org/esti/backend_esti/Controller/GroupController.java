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

    @Autowired
    private GroupService groupService;

    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @PostMapping("/create-group")
    public ResponseEntity createGroup(@RequestBody @Valid GroupForm form) {
        GroupDTO groupDTO = groupService.createGroup(form);
        return ResponseEntity.ok().body(groupDTO);
    }

    @PatchMapping("/{groupId}")
    public ResponseEntity updateGroup (@RequestBody @Valid GroupForm form, @PathVariable("groupId") final Long groupId) throws Exception {
        GroupDTO groupDTO = groupService.updateGroup(form, groupId);
        return ResponseEntity.ok().body(groupId);
    }

    @DeleteMapping("/{groupId}/hard")
    public ResponseEntity deleteGroup(@PathVariable("groupId") final Long groupId) throws Exception {
        groupService.deleteGroup(groupId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{groupId}")
    public ResponseEntity<Void> deleteGroupLogically(@PathVariable Long groupId) throws Exception{
        groupService.deleteGroupLogically(groupId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{groupId}")
    public ResponseEntity findById(@PathVariable("groupId") final Long groupId) throws Exception {
        GroupDTO groupDTO = groupService.findById(groupId);
        return ResponseEntity.ok().body(groupId);
    }

    @GetMapping("/getAll")
    public ResponseEntity getAllGroups() throws Exception {
        List<GroupDTO> groupsDTO = groupService.getAllGroups();
        return ResponseEntity.ok().body(groupsDTO);
    }

    @GetMapping("/active")
    public ResponseEntity<List<GroupDTO>> getAllActiveGroups() {
        List<Group> activeGroups = groupService.getAllActiveGroups();
        List<GroupDTO> activeGroupDTOs = activeGroups.stream().map(GroupDTO::build).toList();
        return ResponseEntity.ok(activeGroupDTOs);
    }
}
