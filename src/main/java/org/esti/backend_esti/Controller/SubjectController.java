package org.esti.backend_esti.Controller;

import jakarta.validation.Valid;
import org.esti.backend_esti.DTO.SubjectDTO;
import org.esti.backend_esti.Entity.Subject;
import org.esti.backend_esti.Form.SubjectForm;
import org.esti.backend_esti.Service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/esti/subject")
public class SubjectController {

    @Autowired
    private SubjectService subjectService;

    @PostMapping("/create")
    public ResponseEntity<SubjectDTO> createSubject(@RequestBody @Valid SubjectForm form) {
        SubjectDTO subjectDTO = subjectService.createSubject(form);
        return ResponseEntity.ok().body(subjectDTO);
    }

    @PatchMapping("/{subjectId}")
    public ResponseEntity<SubjectDTO> updateSubject(@RequestBody @Valid SubjectForm form, @PathVariable("subjectId") final Long subjectId) throws Exception {
        SubjectDTO subjectDTO = subjectService.updateSubject(form, subjectId);
        return ResponseEntity.ok().body(subjectDTO);
    }

    @DeleteMapping("/{subjectId}/hard")
    public ResponseEntity<Void> deleteSubject(@PathVariable("subjectId") final Long subjectId) throws Exception {
        subjectService.deleteSubject(subjectId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{subjectId}")
    public ResponseEntity<Void> deleteSubjectLogically(@PathVariable Long subjectId) throws Exception {
        subjectService.deleteSubjectLogically(subjectId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{subjectId}")
    public ResponseEntity<SubjectDTO> findById(@PathVariable("subjectId") final Long subjectId) throws Exception {
        SubjectDTO subjectDTO = subjectService.findById(subjectId);
        return ResponseEntity.ok().body(subjectDTO);
    }

    @GetMapping("/all")
    public ResponseEntity<List<SubjectDTO>> getAllSubjects() {
        List<SubjectDTO> subjectsDTO = subjectService.getAllSubjects();
        return ResponseEntity.ok().body(subjectsDTO);
    }

    @GetMapping("/active")
    public ResponseEntity<List<SubjectDTO>> getAllActiveSubjects() {
        List<Subject> activeSubjects = subjectService.getAllActiveSubjects();
        List<SubjectDTO> activeSubjectDTOs = activeSubjects.stream().map(SubjectDTO::build).toList();
        return ResponseEntity.ok(activeSubjectDTOs);
    }
}
