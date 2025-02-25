package org.esti.backend_esti.Controller;

import jakarta.validation.Valid;
import org.esti.backend_esti.DTO.StudentDTO;
import org.esti.backend_esti.Form.StudentForm;
import org.esti.backend_esti.Service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/esti/student")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @PostMapping("/create")
    public ResponseEntity<StudentDTO> createStudent(@RequestBody @Valid StudentForm form) {
        StudentDTO studentDTO = studentService.createStudent(form);
        return ResponseEntity.ok().body(studentDTO);
    }

    @PatchMapping("/{studentId}")
    public ResponseEntity<StudentDTO> updateStudent(@RequestBody @Valid StudentForm form, @PathVariable("studentId") final Long studentId) throws Exception {
        StudentDTO studentDTO = studentService.updateStudent(form, studentId);
        return ResponseEntity.ok().body(studentDTO);
    }

    @DeleteMapping("/{studentId}")
    public ResponseEntity<Void> deleteStudent(@PathVariable("studentId") final Long studentId) throws Exception {
        studentService.deleteStudent(studentId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{studentId}")
    public ResponseEntity<StudentDTO> findById(@PathVariable("studentId") final Long studentId) throws Exception {
        StudentDTO studentDTO = studentService.findById(studentId);
        return ResponseEntity.ok().body(studentDTO);
    }

    @GetMapping("/all")
    public ResponseEntity<List<StudentDTO>> getAllStudents() throws Exception {
        List<StudentDTO> studentsDTO = studentService.getAllStudents();
        return ResponseEntity.ok().body(studentsDTO);
    }
} 