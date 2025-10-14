package org.esti.backend_esti.Service;

import org.esti.backend_esti.DTO.AdminDTO;
import org.esti.backend_esti.DTO.StudentDTO;
import org.esti.backend_esti.Entity.Admin;
import org.esti.backend_esti.Entity.Group;
import org.esti.backend_esti.Entity.Student;
import org.esti.backend_esti.Form.StudentForm;
import org.esti.backend_esti.Repository.GroupRepository;
import org.esti.backend_esti.Repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private GroupRepository groupRepository;


    public StudentDTO createStudent(final StudentForm form) {
        Group group = groupRepository.findById(form.getGroupId())
                .orElseThrow(() -> new RuntimeException("Group not found with id: " + form.getGroupId()));
        final Student student = new Student(form, group);
        student.setCreatedAt(LocalDateTime.now());
        studentRepository.save(student);
        return StudentDTO.build(student);
    }

    public StudentDTO updateStudent(final StudentForm form, Long idStudent) throws Exception {
        validateIfStudentExists(idStudent);
        final Student student = studentRepository.findById(idStudent).get();
        student.updateStudent(form);
        studentRepository.save(student);
        return StudentDTO.build(student);
    }

    public void deleteStudent(final Long idStudent) throws Exception {
        validateIfStudentExists(idStudent);
        studentRepository.deleteById(idStudent);
    }

    public StudentDTO findById(Long idStudent) throws Exception {
        validateIfStudentExists(idStudent);
        final Student student = studentRepository.findById(idStudent).orElseThrow(() ->
                new Exception("Student not found with id: " + idStudent)
        );
        return StudentDTO.build(student);
    }

    public StudentDTO findAnyById (Long id) throws Exception {
        Student student = studentRepository.findAnyById(id);
        if (student == null) {
            throw new Exception("Student not found with id: " + id);
        }
        return StudentDTO.build(student);
    }

    public Page<StudentDTO> getStudents(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Student> studentsPage = studentRepository.findAll(pageable);
        return studentsPage.map(StudentDTO::build);
    }

    public Page<StudentDTO> searchStudents(String keyword, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Student> studentsPage = studentRepository.searchStudents(keyword, pageable);
        return studentsPage.map(StudentDTO::build);
    }

    public void deleteStudentLogically(Long idStudent) throws Exception {
        Student existingStudent = studentRepository.findById(idStudent).orElseThrow(() ->
                new Exception("Student not found with id: " + idStudent)
        );
        existingStudent.markAsDeleted();
        studentRepository.save(existingStudent);
    }

    public void validateIfStudentExists(Long idStudent) throws Exception {
        if (!studentRepository.existsById(idStudent)) {
            throw new Exception("Student Not Found");
        }
    }
} 