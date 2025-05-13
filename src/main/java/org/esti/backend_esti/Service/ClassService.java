package org.esti.backend_esti.Service;

import org.esti.backend_esti.DTO.ClassDTO;
import org.esti.backend_esti.Entity.CourseClass;
import org.esti.backend_esti.Form.ClassForm;
import org.esti.backend_esti.Repository.ClassRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ClassService {

    @Autowired
    private ClassRepository classRepository;

    public ClassService(ClassRepository classRepository) {
        this.classRepository = classRepository;
    }

    public ClassDTO createClass(final ClassForm form) {
        final CourseClass classEntity = new CourseClass(form);
        classRepository.save(classEntity);
        return ClassDTO.build(classEntity);
    }

    public ClassDTO updateClass(final ClassForm form, Long idClass) throws Exception {
        validateIfClassExists(idClass);
        final CourseClass classEntity = classRepository.findById(idClass).get();
        classEntity.updateClass(form);
        classRepository.save(classEntity);
        return ClassDTO.build(classEntity);
    }

    public void deleteClass(final Long idClass) throws Exception {
        validateIfClassExists(idClass);
        classRepository.deleteById(idClass);
    }

    public ClassDTO findById(Long idClass) throws Exception {
        validateIfClassExists(idClass);
        final CourseClass classEntity = classRepository.findById(idClass).orElseThrow(() ->
            new Exception("Class not found with id: " + idClass)
        );
        return ClassDTO.build(classEntity);
    }

    @Transactional
    public List<ClassDTO> getAllClasses() throws Exception {
        final List<CourseClass> classes = classRepository.findAll();
        return classes.stream().map(ClassDTO::build).toList();
    }

    @Transactional
    public List<CourseClass> getAllActiveClasses() {
        return classRepository.findAllActive();
    }

    public void deleteClassLogically(Long idClass) throws Exception {
        CourseClass existingClass = classRepository.findById(idClass).orElseThrow(() ->
                new Exception("Class not found with id: " + idClass)
        );
        existingClass.markAsDeleted();
        classRepository.save(existingClass);
    }

    public void validateIfClassExists(Long idClass) throws Exception {
        if (!classRepository.existsById(idClass)) {
            throw new Exception("Class Not Found");
        }
    }
} 