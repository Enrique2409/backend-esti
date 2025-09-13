package org.esti.backend_esti.Controller;

import jakarta.validation.Valid;
import org.esti.backend_esti.DTO.TeacherSubjectGroupDTO;
import org.esti.backend_esti.Entity.TeacherSubjectGroup;
import org.esti.backend_esti.Form.TeacherSubjectGroupForm;
import org.esti.backend_esti.Service.TeacherSubjectGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/esti/tsg")
public class TeacherSubjectGroupController {

    private final TeacherSubjectGroupService teacherSubjectGroupService;

    @Autowired
    public TeacherSubjectGroupController(TeacherSubjectGroupService teacherSubjectGroupService) {
        this.teacherSubjectGroupService = teacherSubjectGroupService;
    }

    @PostMapping("/create-tsg")
    public ResponseEntity<TeacherSubjectGroupDTO> createTeacherSubjectGroup(@RequestBody @Valid TeacherSubjectGroupForm form) throws Exception {
        TeacherSubjectGroupDTO teacherSubjectGroupDTO = teacherSubjectGroupService.createTeacherSubjectGroup(form);
        return ResponseEntity.ok(teacherSubjectGroupDTO);
    }

    @PatchMapping("/{teacherSubjectGroupId}")
    public ResponseEntity<TeacherSubjectGroupDTO> updateTeacherSubjectGroup(@RequestBody @Valid TeacherSubjectGroupForm form, @PathVariable("teacherSubjectGroupId") Long teacherSubjectGroupId) throws Exception {
        TeacherSubjectGroupDTO teacherSubjectGroupDTO = teacherSubjectGroupService.updateTeacherSubjectGroup(form, teacherSubjectGroupId);
        return ResponseEntity.ok(teacherSubjectGroupDTO);
    }

    @DeleteMapping("/{teacherSubjectGroupId}/hard")
    public ResponseEntity<Void> deleteTeacherSubjectGroup(@PathVariable("teacherSubjectGroupId") Long teacherSubjectGroupId) throws Exception {
        teacherSubjectGroupService.deleteTeacherSubjectGroup(teacherSubjectGroupId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{teacherSubjectGroupId}")
    public ResponseEntity<Void> deleteTeacherSubjectGroupLogically(@PathVariable("teacherSubjectGroupId") Long teacherSubjectGroupId) throws Exception {
        teacherSubjectGroupService.deleteTeacherSubjectGroupLogically(teacherSubjectGroupId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{teacherSubjectGroupId}")
    public ResponseEntity<TeacherSubjectGroupDTO> findById(@PathVariable("teacherSubjectGroupId") Long teacherSubjectGroupId) throws Exception {
        TeacherSubjectGroupDTO teacherSubjectGroupDTO = teacherSubjectGroupService.findById(teacherSubjectGroupId);
        return ResponseEntity.ok(teacherSubjectGroupDTO);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<TeacherSubjectGroupDTO>> getAllTeacherSubjectGroup() {
        List<TeacherSubjectGroupDTO> teacherSubjectGroupDTOList = teacherSubjectGroupService.getAllTeacherSubjectGroup();
        return ResponseEntity.ok(teacherSubjectGroupDTOList);
    }

    @GetMapping("/by-group/{groupId}")
    public ResponseEntity<List<TeacherSubjectGroupDTO>> getByGroup(@PathVariable("groupId") Long groupId) {
        List<TeacherSubjectGroupDTO> filtered = teacherSubjectGroupService.getByGroup(groupId);
        return ResponseEntity.ok(filtered);
    }

    @GetMapping("/by-teacher/{teacherId}")
    public ResponseEntity<List<TeacherSubjectGroupDTO>> getByTeacher(@PathVariable("teacherId") Long teacherId) {
        List<TeacherSubjectGroupDTO> filtered = teacherSubjectGroupService.getByTeacher(teacherId);
        return ResponseEntity.ok(filtered);
    }

    @GetMapping("/by-subject/{subjectId}")
    public ResponseEntity<List<TeacherSubjectGroupDTO>> getBySubject(@PathVariable("subjectId") Long subjectId) {
        List<TeacherSubjectGroupDTO> filtered = teacherSubjectGroupService.getBySubject(subjectId);
        return ResponseEntity.ok(filtered);
    }

    @GetMapping("/teacher/{teacherId}/group/{groupId}")
    public List<TeacherSubjectGroup> getByTeacherAndGroup(@PathVariable("teacherId") Long teacherId, @PathVariable("groupId") Long groupId) {
        return teacherSubjectGroupService.getByTeacherAndGroup(teacherId, groupId);
    }

    @GetMapping("/teacher/{teacherId}/subject/{subjectId}")
    public List<TeacherSubjectGroup> getByTeacherAndSubject(@PathVariable("teacherId") Long teacherId, @PathVariable("subjectId") Long subjectId) {
        return teacherSubjectGroupService.getByTeacherAndSubject(teacherId, subjectId);
    }

    @GetMapping("/group/{groupId}/subject/{subjectId}")
    public TeacherSubjectGroup getByGroupAndSubject(@PathVariable("groupId") Long groupId, @PathVariable("subjectId") Long subjectId) {
        return teacherSubjectGroupService.getByGroupAndSubject(groupId, subjectId);
    }
}
