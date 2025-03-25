package org.esti.backend_esti.Controller;

import jakarta.validation.Valid;
import org.esti.backend_esti.DTO.GroupDTO;
import org.esti.backend_esti.DTO.SubjectTeacherDTO;
import org.esti.backend_esti.Entity.Group;
import org.esti.backend_esti.Entity.SubjectTeacher;
import org.esti.backend_esti.Form.SubjectTeacherForm;
import org.esti.backend_esti.Service.SubjectTeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/esti/subject-teacher")
public class SubjectTeacherController {

    @Autowired
    private SubjectTeacherService subjectTeacherService;

    @PostMapping("/create")
    public ResponseEntity<SubjectTeacherDTO> createSubjectTeacher(@RequestBody @Valid SubjectTeacherForm form) {
        SubjectTeacherDTO subjectTeacherDTO = subjectTeacherService.createSubjectTeacher(form);
        return ResponseEntity.ok().body(subjectTeacherDTO);
    }

    @PatchMapping("/{subjectTeacherId}")
    public ResponseEntity<SubjectTeacherDTO> updateSubjectTeacher(@RequestBody @Valid SubjectTeacherForm form, @PathVariable("subjectTeacherId") final Long subjectTeacherId) throws Exception {
        SubjectTeacherDTO subjectTeacherDTO = subjectTeacherService.updateSubjectTeacher(form, subjectTeacherId);
        return ResponseEntity.ok().body(subjectTeacherDTO);
    }

    @DeleteMapping("/{subjectTeacherId}")
    public ResponseEntity<Void> deleteSubjectTeacher(@PathVariable("subjectTeacherId") final Long subjectTeacherId) throws Exception {
        subjectTeacherService.deleteSubjectTeacher(subjectTeacherId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{groupId}/hard")
    public ResponseEntity<Void> deleteSubjectTeacherLogically(@PathVariable Long subjectTeacherId) throws Exception{
        subjectTeacherService.deleteSubjectTeacherLogically(subjectTeacherId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{subjectTeacherId}")
    public ResponseEntity<SubjectTeacherDTO> findById(@PathVariable("subjectTeacherId") final Long subjectTeacherId) throws Exception {
        SubjectTeacherDTO subjectTeacherDTO = subjectTeacherService.findById(subjectTeacherId);
        return ResponseEntity.ok().body(subjectTeacherDTO);
    }

    @GetMapping("/all")
    public ResponseEntity<List<SubjectTeacherDTO>> getAllSubjectTeachers() throws Exception {
        List<SubjectTeacherDTO> subjectTeachersDTO = subjectTeacherService.getAllSubjectTeachers();
        return ResponseEntity.ok().body(subjectTeachersDTO);
    }

    @GetMapping("/active")
    public ResponseEntity<List<SubjectTeacherDTO>> getAllActiveSubjectTeachers() {
        List<SubjectTeacher> activeSubjectTeachers = subjectTeacherService.getAllActiveSubjectTeachers();
        List<SubjectTeacherDTO> activeSubjectTeachersDTO = activeSubjectTeachers.stream().map(SubjectTeacherDTO::build).toList();
        return ResponseEntity.ok(activeSubjectTeachersDTO);
    }
} 