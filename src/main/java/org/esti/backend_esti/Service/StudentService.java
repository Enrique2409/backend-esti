package org.esti.backend_esti.Service;

import org.esti.backend_esti.DTO.StudentDTO;
import org.esti.backend_esti.Entity.Group;
import org.esti.backend_esti.Entity.Student;
import org.esti.backend_esti.Form.StudentForm;
import org.esti.backend_esti.Repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    public StudentDTO createStudent(final StudentForm form) {
        final Student student = new Student(form);
        studentRepository.save(student);
        student.setCreatedAt(LocalDateTime.now());
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

    public List<StudentDTO> getAllStudents() throws Exception {
        final List<Student> students = studentRepository.findAll();
        return students.stream().map(StudentDTO::build).toList();
    }

    public List<Student> getAllActiveStudents() {
        return studentRepository.findAllActive();
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