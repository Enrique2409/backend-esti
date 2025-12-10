package org.esti.backend_esti.Service;

import org.esti.backend_esti.DTO.TeacherSubjectDTO;
import org.esti.backend_esti.Entity.*;
import org.esti.backend_esti.Form.TeacherSubjectForm;
import org.esti.backend_esti.Repository.GroupRepository;
import org.esti.backend_esti.Repository.SubjectRepository;
import org.esti.backend_esti.Repository.TeacherRepository;
import org.esti.backend_esti.Repository.TeacherSubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TeacherSubjectService {

    private final TeacherSubjectRepository teacherSubjectRepository;
    private final TeacherRepository teacherRepository;
    private final SubjectRepository subjectRepository;


    @Autowired
    public TeacherSubjectService(
            TeacherSubjectRepository teacherSubjectRepository,
            GroupRepository groupRepository,
            TeacherRepository teacherRepository,
            SubjectRepository subjectRepository
    ) {
        this.teacherSubjectRepository = teacherSubjectRepository;
        this.teacherRepository = teacherRepository;
        this.subjectRepository = subjectRepository;
    }

    public TeacherSubjectDTO createTeacherSubject(TeacherSubjectForm form) throws Exception {
        Teacher teacher = teacherRepository.findById(form.getTeacherId())
                .orElseThrow(() -> new Exception("Teacher not found with id: " + form.getTeacherId()));
        Subject subject = subjectRepository.findById(form.getSubjectId())
                .orElseThrow(() -> new Exception("Subject not found with id: " + form.getSubjectId()));

        TeacherSubject teacherSubject = new TeacherSubject(form, teacher, subject);
        teacherSubject.setCreatedAt(LocalDateTime.now());
        teacherSubjectRepository.save(teacherSubject);
        return TeacherSubjectDTO.build(teacherSubject);
    }

    public TeacherSubjectDTO updateTeacherSubject(TeacherSubjectForm form, Long idTeacherSubject) throws Exception {
        validateIfTSGExists(idTeacherSubject);
        TeacherSubject teacherSubject = teacherSubjectRepository.findById(idTeacherSubject).get();

        Teacher teacher = teacherRepository.findById(form.getTeacherId())
                .orElseThrow(() -> new Exception("Teacher not found with id: " + form.getTeacherId()));
        Subject subject = subjectRepository.findById(form.getSubjectId())
                .orElseThrow(() -> new Exception("Subject not found with id: " + form.getSubjectId()));

        teacherSubject.updateFromForm(form, teacher, subject);
        teacherSubjectRepository.save(teacherSubject);
        return TeacherSubjectDTO.build(teacherSubject);
    }

    public void deleteTeacherSubject(Long idTeacherSubject) throws Exception {
        validateIfTSGExists(idTeacherSubject);
        teacherSubjectRepository.deleteById(idTeacherSubject);
    }

    public void deleteTeacherSubjectLogically(Long idTeacherSubject) throws Exception {
        TeacherSubject teacherSubject = teacherSubjectRepository.findById(idTeacherSubject)
                .orElseThrow(() -> new Exception("TeacherSubject not found with id: " + idTeacherSubject));
        teacherSubject.markAsDeleted();
        teacherSubjectRepository.save(teacherSubject);
    }

    public TeacherSubjectDTO findById(Long idTeacherSubject) throws Exception {
        validateIfTSGExists(idTeacherSubject);
        TeacherSubject teacherSubject = teacherSubjectRepository.findById(idTeacherSubject)
                .orElseThrow(() -> new Exception("TeacherSubject not found with id: " + idTeacherSubject));
        return TeacherSubjectDTO.build(teacherSubject);
    }

    public List<TeacherSubjectDTO> getAllTeacherSubject() {
        return teacherSubjectRepository.findAll()
                .stream()
                .map(TeacherSubjectDTO::build)
                .toList();
    }

    public List<TeacherSubject> getAllActiveTeacherSubjects() {
        return teacherSubjectRepository.findAllActive();
    }

    public List<TeacherSubjectDTO> getByTeacher(Long teacherId) {
        return teacherSubjectRepository.findByTeacherId(teacherId)
                .stream()
                .map(TeacherSubjectDTO::build)
                .toList();
    }

    public List<TeacherSubjectDTO> getBySubject(Long subjectId) {
        return teacherSubjectRepository.findBySubjectId(subjectId)
                .stream()
                .map(TeacherSubjectDTO::build)
                .toList();
    }

    public List<TeacherSubject> getByTeacherAndSubject(Long teacherId, Long subjectId) {
        return teacherSubjectRepository.findByTeacherIdAndSubjectId(teacherId, subjectId);
    }

    public void validateIfTSGExists(Long idTeacherSubject) throws Exception {
        if (!teacherSubjectRepository.existsById(idTeacherSubject)) {
            throw new Exception("TeacherSubject not found with id: " + idTeacherSubject);
        }
    }
}
