package org.esti.backend_esti.Controller;

import jakarta.validation.Valid;
import org.esti.backend_esti.DTO.GradesDTO;
import org.esti.backend_esti.Form.GradesForm;
import org.esti.backend_esti.Service.GradesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/esti/grades")
public class GradesController {

    @Autowired
    private GradesService gradesService;

    @PostMapping("/create")
    public ResponseEntity<GradesDTO> createGrade(@RequestBody @Valid GradesForm form) {
        GradesDTO gradesDTO = gradesService.createGrade(form);
        return ResponseEntity.ok().body(gradesDTO);
    }

    @PatchMapping("/{gradeId}")
    public ResponseEntity<GradesDTO> updateGrade(@RequestBody @Valid GradesForm form, @PathVariable("gradeId") final Long gradeId) throws Exception {
        GradesDTO gradesDTO = gradesService.updateGrades(form, gradeId);
        return ResponseEntity.ok().body(gradesDTO);
    }

    @DeleteMapping("/{gradeId}")
    public ResponseEntity<Void> deleteGrade(@PathVariable("gradeId") final Long gradeId) throws Exception {
        gradesService.deleteGrade(gradeId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{gradeId}")
    public ResponseEntity<GradesDTO> findById(@PathVariable("gradeId") final Long gradeId) throws Exception {
        GradesDTO gradesDTO = gradesService.findById(gradeId);
        return ResponseEntity.ok().body(gradesDTO);
    }

    @GetMapping("/all")
    public ResponseEntity<List<GradesDTO>> getAllGrades() throws Exception {
        List<GradesDTO> gradesDTO = gradesService.getAllGrades();
        return ResponseEntity.ok().body(gradesDTO);
    }
} 