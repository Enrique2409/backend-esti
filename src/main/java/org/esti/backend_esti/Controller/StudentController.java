package org.esti.backend_esti.Controller;

import jakarta.validation.Valid;
import org.esti.backend_esti.DTO.StudentDTO;
import org.esti.backend_esti.Form.StudentForm;
import org.esti.backend_esti.Service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/esti/student")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @PostMapping("/create-student")
    public ResponseEntity<StudentDTO> createStudent(@RequestBody @Valid StudentForm form) {
        StudentDTO studentDTO = studentService.createStudent(form);
        return ResponseEntity.ok().body(studentDTO);
    }

    @PatchMapping("/{studentId}")
    public ResponseEntity<StudentDTO> updateStudent(@RequestBody @Valid StudentForm form, @PathVariable("studentId") final Long studentId) throws Exception {
        StudentDTO studentDTO = studentService.updateStudent(form, studentId);
        return ResponseEntity.ok().body(studentDTO);
    }

    @DeleteMapping("/{studentId}/hard")
    public ResponseEntity<Void> deleteStudent(@PathVariable("studentId") final Long studentId) throws Exception {
        studentService.deleteStudent(studentId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{studentId}")
    public ResponseEntity<Void> deleteStudentLogically(@PathVariable Long studentId) throws Exception{
        studentService.deleteStudentLogically(studentId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{studentId}")
    public ResponseEntity<StudentDTO> findById(@PathVariable("studentId") final Long studentId) throws Exception {
        StudentDTO studentDTO = studentService.findById(studentId);
        return ResponseEntity.ok().body(studentDTO);
    }

    @GetMapping("/{studentId}/any")
    public ResponseEntity<StudentDTO> findAnyById(@PathVariable("studentId") Long studentId) throws Exception {
        StudentDTO studentDTO = studentService.findAnyById(studentId);
        return ResponseEntity.ok().body(studentDTO);
    }

    @GetMapping("/")
    public ResponseEntity<Page<StudentDTO>> getStudents(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "30") int size
    ) {
        Page<StudentDTO> studentsPage = studentService.getStudents(page, size);
        return ResponseEntity.ok(studentsPage);
    }

    @GetMapping("/search")
    public Page<StudentDTO> searchStudents(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return studentService.searchStudents(keyword, page, size);
    }


} 