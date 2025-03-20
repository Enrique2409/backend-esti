package org.esti.backend_esti.Controller;

import jakarta.validation.Valid;
import org.esti.backend_esti.DTO.ClassDTO;
import org.esti.backend_esti.Entity.CourseClass;
import org.esti.backend_esti.Form.ClassForm;
import org.esti.backend_esti.Service.ClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/esti/class")
public class ClassController {

    @Autowired
    private ClassService classService;

    @PostMapping("/create-class")
    public ResponseEntity<ClassDTO> createClass(@RequestBody @Valid ClassForm form) {
        ClassDTO classDTO = classService.createClass(form);
        return ResponseEntity.ok().body(classDTO);
    }

    @PatchMapping("/{classId}")
    public ResponseEntity<ClassDTO> updateClass(@RequestBody @Valid ClassForm form, @PathVariable("classId") final Long classId) throws Exception {
        ClassDTO classDTO = classService.updateClass(form, classId);
        return ResponseEntity.ok().body(classDTO);
    }

    @DeleteMapping("/{classId}/hard")
    public ResponseEntity<Void> deleteClass(@PathVariable("classId") final Long classId) throws Exception {
        classService.deleteClass(classId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{classId}")
    public ResponseEntity<Void> deleteClassLogically(@PathVariable Long classId) throws Exception {
        classService.deleteClassLogically(classId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{classId}")
    public ResponseEntity<ClassDTO> findById(@PathVariable("classId") final Long classId) throws Exception {
        ClassDTO classDTO = classService.findById(classId);
        return ResponseEntity.ok().body(classDTO);
    }

    @GetMapping("/all")
    public ResponseEntity<List<ClassDTO>> getAllClasses() throws Exception {
        List<ClassDTO> classesDTO = classService.getAllClasses();
        return ResponseEntity.ok().body(classesDTO);
    }

    @GetMapping("/active")
    public ResponseEntity <List<ClassDTO>> getAllActiveClasses() {
        List<CourseClass> activeClasses = classService.getAllActiveClasses();
        List<ClassDTO> activeClassesDTO = activeClasses.stream().map(ClassDTO::build).toList();
        return ResponseEntity.ok(activeClassesDTO);
    }
} 