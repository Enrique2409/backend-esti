package org.esti.backend_esti.Service;

import org.esti.backend_esti.DTO.TeacherDTO;
import org.esti.backend_esti.Entity.Group;
import org.esti.backend_esti.Entity.Teacher;
import org.esti.backend_esti.Form.TeacherForm;
import org.esti.backend_esti.Repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TeacherService {

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public TeacherDTO createTeacher(final TeacherForm form) {
        final Teacher teacher = new Teacher(form);
        teacher.setPassword(passwordEncoder.encode(form.getPassword()));
        teacherRepository.save(teacher);
        teacher.setCreatedAt(LocalDateTime.now());
        return TeacherDTO.build(teacher);
    }

    public TeacherDTO updateTeacher(final TeacherForm form, Long idTeacher) throws Exception {
        validateIfTeacherExists(idTeacher);
        final Teacher teacher = teacherRepository.findById(idTeacher).get();
        teacher.setPassword(passwordEncoder.encode(form.getPassword()));
        teacher.updateTeacher(form);
        teacherRepository.save(teacher);
        return TeacherDTO.build(teacher);
    }

    public void deleteTeacher(final Long idTeacher) throws Exception {
        validateIfTeacherExists(idTeacher);
        teacherRepository.deleteById(idTeacher);
    }

    public TeacherDTO findById(Long idTeacher) throws Exception {
        validateIfTeacherExists(idTeacher);
        final Teacher teacher = teacherRepository.findById(idTeacher).orElseThrow(() ->
            new Exception("Teacher not found with id: " + idTeacher)
        );
        return TeacherDTO.build(teacher);
    }

    public List<TeacherDTO> getAllTeachers() throws Exception {
        final List<Teacher> teachers = teacherRepository.findAll();
        return teachers.stream().map(TeacherDTO::build).toList();
    }

    public List<Teacher> getAllActiveTeachers() {
        return teacherRepository.findAllActive();
    }

    public void deleteTeacherLogically(Long idTeacher) throws Exception {
        Teacher existingTeacher = teacherRepository.findById(idTeacher).orElseThrow(() ->
                new Exception("Group not found with id: " + idTeacher)
        );
        existingTeacher.markAsDeleted();
        teacherRepository.save(existingTeacher);
    }

    public void validateIfTeacherExists(Long idTeacher) throws Exception {
        if (!teacherRepository.existsById(idTeacher)) {
            throw new Exception("Teacher Not Found");
        }
    }
} 