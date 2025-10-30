package org.esti.backend_esti.Controller;

import jakarta.validation.Valid;
import org.esti.backend_esti.DTO.TeacherDTO;
import org.esti.backend_esti.Form.TeacherForm;
import org.esti.backend_esti.Service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/esti/teacher")
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    @PostMapping("/create")
    public ResponseEntity<TeacherDTO> createTeacher(@RequestBody @Valid TeacherForm form) {
        TeacherDTO teacherDTO = teacherService.createTeacher(form);
        return ResponseEntity.ok().body(teacherDTO);
    }

    @PatchMapping("/{teacherId}")
    public ResponseEntity<TeacherDTO> updateTeacher(@RequestBody @Valid TeacherForm form, @PathVariable("teacherId") final Long teacherId) throws Exception {
        TeacherDTO teacherDTO = teacherService.updateTeacher(form, teacherId);
        return ResponseEntity.ok().body(teacherDTO);
    }

    @DeleteMapping("/{teacherId}/hard")
    public ResponseEntity<Void> deleteTeacher(@PathVariable("teacherId") final Long teacherId) throws Exception {
        teacherService.deleteTeacher(teacherId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{teacherId}")
    public ResponseEntity<Void> deleteTeacherLogically(@PathVariable Long teacherId) throws Exception{
        teacherService.deleteTeacherLogically(teacherId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{teacherId}")
    public ResponseEntity<TeacherDTO> findById(@PathVariable("teacherId") final Long teacherId) throws Exception {
        TeacherDTO teacherDTO = teacherService.findById(teacherId);
        return ResponseEntity.ok().body(teacherDTO);
    }

    @GetMapping("/{teacherId}/any")
    public ResponseEntity<TeacherDTO> findAnyById(@PathVariable("teacherId") Long teacherId) throws Exception {
        TeacherDTO teacherDTO = teacherService.findAnyById(teacherId);
        return ResponseEntity.ok(teacherDTO);
    }

    @GetMapping("/")
    public ResponseEntity<Page<TeacherDTO>> getTeachers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "30") int size
    ) {
        Page<TeacherDTO> teachersPage = teacherService.getTeachers(page, size);
        return ResponseEntity.ok(teachersPage);
    }

    @GetMapping("/search")
    public Page<TeacherDTO> searchTeachers(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return teacherService.searchTeachers(keyword, page, size);
    }
} 