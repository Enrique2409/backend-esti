package org.esti.backend_esti.Service;

import org.esti.backend_esti.DTO.SubjectTeacherDTO;
import org.esti.backend_esti.Entity.Group;
import org.esti.backend_esti.Entity.Subject;
import org.esti.backend_esti.Entity.SubjectTeacher;
import org.esti.backend_esti.Form.SubjectTeacherForm;
import org.esti.backend_esti.Repository.SubjectTeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubjectTeacherService {

    @Autowired
    private SubjectTeacherRepository subjectTeacherRepository;

    public SubjectTeacherDTO createSubjectTeacher(final SubjectTeacherForm form) {
        final SubjectTeacher subjectTeacher = new SubjectTeacher(form);
        subjectTeacherRepository.save(subjectTeacher);
        return SubjectTeacherDTO.build(subjectTeacher);
    }

    public SubjectTeacherDTO updateSubjectTeacher(final SubjectTeacherForm form, Long idSubjectTeacher) throws Exception {
        validateIfSubjectTeacherExists(idSubjectTeacher);
        final SubjectTeacher subjectTeacher = subjectTeacherRepository.findById(idSubjectTeacher).get();
        subjectTeacher.updateSubjectTeacher(form);
        subjectTeacherRepository.save(subjectTeacher);
        return SubjectTeacherDTO.build(subjectTeacher);
    }

    public void deleteSubjectTeacher(final Long idSubjectTeacher) throws Exception {
        validateIfSubjectTeacherExists(idSubjectTeacher);
        subjectTeacherRepository.deleteById(idSubjectTeacher);
    }

    public SubjectTeacherDTO findById(Long idSubjectTeacher) throws Exception {
        validateIfSubjectTeacherExists(idSubjectTeacher);
        final SubjectTeacher subjectTeacher = subjectTeacherRepository.findById(idSubjectTeacher).orElseThrow(() ->
            new Exception("SubjectTeacher not found with id: " + idSubjectTeacher)
        );
        return SubjectTeacherDTO.build(subjectTeacher);
    }

    public List<SubjectTeacherDTO> getAllSubjectTeachers() throws Exception {
        final List<SubjectTeacher> subjectTeachers = subjectTeacherRepository.findAll();
        return subjectTeachers.stream().map(SubjectTeacherDTO::build).toList();
    }

    public List<SubjectTeacher> getAllActiveSubjectTeachers() {
        return subjectTeacherRepository.findAllActive();
    }

    public void deleteSubjectTeacherLogically(Long idSubjectTeacher) throws Exception {
        SubjectTeacher existingSubjectTeacher = subjectTeacherRepository.findById(idSubjectTeacher).orElseThrow(() ->
                new Exception("Group not found with id: " + idSubjectTeacher)
        );
        existingSubjectTeacher.markAsDeleted();
        subjectTeacherRepository.save(existingSubjectTeacher);
    }

    public void validateIfSubjectTeacherExists(Long idSubjectTeacher) throws Exception {
        if (!subjectTeacherRepository.existsById(idSubjectTeacher)) {
            throw new Exception("SubjectTeacher Not Found");
        }
    }
} 