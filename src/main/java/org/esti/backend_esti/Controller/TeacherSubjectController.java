package org.esti.backend_esti.Controller;

import jakarta.validation.Valid;
import org.esti.backend_esti.DTO.TeacherSubjectDTO;
import org.esti.backend_esti.Entity.TeacherSubject;
import org.esti.backend_esti.Form.TeacherSubjectForm;
import org.esti.backend_esti.Service.TeacherSubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/esti/tsg")
public class TeacherSubjectController {

    private final TeacherSubjectService teacherSubjectService;

    @Autowired
    public TeacherSubjectController(TeacherSubjectService teacherSubjectGroupService) {
        this.teacherSubjectService = teacherSubjectGroupService;
    }

    @PostMapping("/create-tsg")
    public ResponseEntity<TeacherSubjectDTO> createTeacherSubject(@RequestBody @Valid TeacherSubjectForm form) throws Exception {
        TeacherSubjectDTO teacherSubjectGroupDTO = teacherSubjectService.createTeacherSubject(form);
        return ResponseEntity.ok(teacherSubjectGroupDTO);
    }

    @PatchMapping("/{teacherSubjectId}")
    public ResponseEntity<TeacherSubjectDTO> updateTeacherSubject(@RequestBody @Valid TeacherSubjectForm form, @PathVariable("teacherSubjectId") Long teacherSubjectId) throws Exception {
        TeacherSubjectDTO teacherSubjectGroupDTO = teacherSubjectService.updateTeacherSubject(form, teacherSubjectId);
        return ResponseEntity.ok(teacherSubjectGroupDTO);
    }

    @DeleteMapping("/{teacherSubjectId}/hard")
    public ResponseEntity<Void> deleteTeacherSubjectGroup(@PathVariable("teacherSubjectId") Long teacherSubjectId) throws Exception {
        teacherSubjectService.deleteTeacherSubject(teacherSubjectId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{teacherSubjectId}")
    public ResponseEntity<Void> deleteTeacherSubjectLogically(@PathVariable("teacherSubjectId") Long teacherSubjectId) throws Exception {
        teacherSubjectService.deleteTeacherSubjectLogically(teacherSubjectId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{teacherSubjectId}")
    public ResponseEntity<TeacherSubjectDTO> findById(@PathVariable("teacherSubjectId") Long teacherSubjectId) throws Exception {
        TeacherSubjectDTO teacherSubjectDTO = teacherSubjectService.findById(teacherSubjectId);
        return ResponseEntity.ok(teacherSubjectDTO);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<TeacherSubjectDTO>> getAllTeacherSubject() {
        List<TeacherSubjectDTO> teacherSubjectDTOList = teacherSubjectService.getAllTeacherSubject();
        return ResponseEntity.ok(teacherSubjectDTOList);
    }

    @GetMapping("/by-teacher/{teacherId}")
    public ResponseEntity<List<TeacherSubjectDTO>> getByTeacher(@PathVariable("teacherId") Long teacherId) {
        List<TeacherSubjectDTO> filtered = teacherSubjectService.getByTeacher(teacherId);
        return ResponseEntity.ok(filtered);
    }

    @GetMapping("/by-subject/{subjectId}")
    public ResponseEntity<List<TeacherSubjectDTO>> getBySubject(@PathVariable("subjectId") Long subjectId) {
        List<TeacherSubjectDTO> filtered = teacherSubjectService.getBySubject(subjectId);
        return ResponseEntity.ok(filtered);
    }

    @GetMapping("/teacher/{teacherId}/subject/{subjectId}")
    public List<TeacherSubject> getByTeacherAndSubject(@PathVariable("teacherId") Long teacherId, @PathVariable("subjectId") Long subjectId) {
        return teacherSubjectService.getByTeacherAndSubject(teacherId, subjectId);
    }
}
