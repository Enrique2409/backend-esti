package org.esti.backend_esti.Service;

import org.esti.backend_esti.DTO.SubjectDTO;
import org.esti.backend_esti.Entity.Subject;
import org.esti.backend_esti.Entity.Teacher;
import org.esti.backend_esti.Form.SubjectForm;
import org.esti.backend_esti.Repository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SubjectService {

    @Autowired
    private SubjectRepository subjectRepository;

    public SubjectDTO createSubject(final SubjectForm form) {
        final Subject subject = new Subject(form);
        subjectRepository.save(subject);
        subject.setCreatedAt(LocalDateTime.now());
        return SubjectDTO.build(subject);
    }

    public SubjectDTO updateSubject(final SubjectForm form, Long idSubject) throws Exception {
        validateIfSubjectExists(idSubject);
        final Subject subject = subjectRepository.findById(idSubject).get();
        subject.updateSubject(form);
        subjectRepository.save(subject);
        return SubjectDTO.build(subject);
    }

    public void deleteSubject(final Long idSubject) throws Exception {
        validateIfSubjectExists(idSubject);
        subjectRepository.deleteById(idSubject);
    }

    public SubjectDTO findById(Long idSubject) throws Exception {
        validateIfSubjectExists(idSubject);
        final Subject subject = subjectRepository.findById(idSubject).orElseThrow(() ->
            new Exception("Subject not found with id: " + idSubject)
        );
        return SubjectDTO.build(subject);
    }

    public List<SubjectDTO> getAllSubjects() throws Exception {
        final List<Subject> subjects = subjectRepository.findAll();
        return subjects.stream().map(SubjectDTO::build).toList();
    }

    public List<Subject> getAllActiveSubjects() {
        return subjectRepository.findAllActive();
    }

    public void deleteSubjectLogically(Long idSubject) throws Exception {
        Subject existingSubject = subjectRepository.findById(idSubject).orElseThrow(() ->
                new Exception("Subject not found with id: " + idSubject)
        );
        existingSubject.markAsDeleted();
        subjectRepository.save(existingSubject);
    }

    public void validateIfSubjectExists(Long idSubject) throws Exception {
        if (!subjectRepository.existsById(idSubject)) {
            throw new Exception("Subject Not Found");
        }
    }
} 