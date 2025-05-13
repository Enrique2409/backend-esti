package org.esti.backend_esti.Service;

import org.esti.backend_esti.DTO.SubjectTeacherDTO;
import org.esti.backend_esti.Entity.Student;
import org.esti.backend_esti.Entity.Subject;
import org.esti.backend_esti.Entity.SubjectTeacherClass;
import org.esti.backend_esti.Entity.Teacher;
import org.esti.backend_esti.Form.SubjectTeacherForm;
import org.esti.backend_esti.Repository.StudentRepository;
import org.esti.backend_esti.Repository.SubjectRepository;
import org.esti.backend_esti.Repository.SubjectTeacherRepository;
import org.esti.backend_esti.Repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SubjectTeacherService {

    @Autowired
    private SubjectTeacherRepository subjectTeacherRepository;

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private StudentRepository studentRepository;

    public SubjectTeacherDTO createSubjectTeacher(final SubjectTeacherForm form) {
        final SubjectTeacherClass subjectTeacher = new SubjectTeacherClass(form);
        subjectTeacherRepository.save(subjectTeacher);
        return SubjectTeacherDTO.build(subjectTeacher);
    }

    @Transactional
    public SubjectTeacherDTO updateSubjectTeacher(final SubjectTeacherForm form, Long idSubjectTeacher) throws Exception {
        validateIfSubjectTeacherExists(idSubjectTeacher);

        // Recupera el objeto SubjectTeacherClass desde la base de datos
        final SubjectTeacherClass subjectTeacher = subjectTeacherRepository.findById(idSubjectTeacher)
                .orElseThrow(() -> new Exception("SubjectTeacher not found with id: " + idSubjectTeacher));

        // Si el estudiante es proporcionado en el formulario, actualiza la relación
        if (form.getStudent() != null && form.getStudent().getIdStudent() != null) {
            // Busca al estudiante por su ID
            Student student = studentRepository.findById(form.getStudent().getIdStudent())
                    .orElseThrow(() -> new Exception("Student not found with id: " + form.getStudent().getIdStudent()));

            // Actualiza la relación entre SubjectTeacherClass y Student
            subjectTeacher.setStudent(student);
        }

        // Aquí puedes manejar otras actualizaciones (por ejemplo, Subject, Teacher, etc.)
        if (form.getSubject() != null) {
            Subject subject = subjectRepository.findById(form.getSubject().getIdSubject())
                    .orElseThrow(() -> new Exception("Subject not found"));
            subjectTeacher.setSubject(subject);
        }

        if (form.getTeacher() != null) {
            Teacher teacher = teacherRepository.findById(form.getTeacher().getIdTeacher())
                    .orElseThrow(() -> new Exception("Teacher not found"));
            subjectTeacher.setTeacher(teacher);
        }

        // Guarda el objeto actualizado
        subjectTeacherRepository.save(subjectTeacher);

        // Devuelve el DTO del SubjectTeacher actualizado
        return SubjectTeacherDTO.build(subjectTeacher);
    }


    public void deleteSubjectTeacher(final Long idSubjectTeacher) throws Exception {
        validateIfSubjectTeacherExists(idSubjectTeacher);
        subjectTeacherRepository.deleteById(idSubjectTeacher);
    }

    public SubjectTeacherDTO findById(Long idSubjectTeacher) throws Exception {
        validateIfSubjectTeacherExists(idSubjectTeacher);
        final SubjectTeacherClass subjectTeacher = subjectTeacherRepository.findById(idSubjectTeacher).orElseThrow(() ->
            new Exception("SubjectTeacher not found with id: " + idSubjectTeacher)
        );
        return SubjectTeacherDTO.build(subjectTeacher);
    }

    public List<SubjectTeacherDTO> getAllSubjectTeachers() throws Exception {
        final List<SubjectTeacherClass> subjectTeachers = subjectTeacherRepository.findAll();
        return subjectTeachers.stream().map(SubjectTeacherDTO::build).toList();
    }

    public List<SubjectTeacherClass> getAllActiveSubjectTeachers() {
        return subjectTeacherRepository.findAllActive();
    }

    public void deleteSubjectTeacherLogically(Long idSubjectTeacher) throws Exception {
        SubjectTeacherClass existingSubjectTeacher = subjectTeacherRepository.findById(idSubjectTeacher).orElseThrow(() ->
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