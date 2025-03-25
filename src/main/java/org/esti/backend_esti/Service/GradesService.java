package org.esti.backend_esti.Service;

import org.esti.backend_esti.DTO.GradesDTO;
import org.esti.backend_esti.Entity.Grades;
import org.esti.backend_esti.Form.GradesForm;
import org.esti.backend_esti.Repository.GradesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GradesService {

    @Autowired
    private GradesRepository gradesRepository;

    public GradesDTO createGrade(final GradesForm form) {
        final Grades grade = new Grades(form);
        gradesRepository.save(grade);
        return GradesDTO.build(grade);
    }

    public GradesDTO updateGrades(final GradesForm form, Long idGrade) throws Exception {
        validateIfGradeExists(idGrade);
        final Grades grade = gradesRepository.findById(idGrade).get();
        grade.updateGrades(form);
        gradesRepository.save(grade);
        return GradesDTO.build(grade);
    }

    public void deleteGrade(final Long idGrade) throws Exception {
        validateIfGradeExists(idGrade);
        gradesRepository.deleteById(idGrade);
    }

    public GradesDTO findById(Long idGrade) throws Exception {
        validateIfGradeExists(idGrade);
        final Grades grade = gradesRepository.findById(idGrade).orElseThrow(() ->
            new Exception("Grade not found with id: " + idGrade)
        );
        return GradesDTO.build(grade);
    }

    public List<GradesDTO> getAllGrades() throws Exception {
        final List<Grades> grades = gradesRepository.findAll();
        return grades.stream().map(GradesDTO::build).toList();
    }

    public void validateIfGradeExists(Long idGrade) throws Exception {
        if (!gradesRepository.existsById(idGrade)) {
            throw new Exception("Grade Not Found");
        }
    }
} 