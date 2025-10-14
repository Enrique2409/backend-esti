package org.esti.backend_esti.Service;

import org.esti.backend_esti.DTO.CardexDTO;
import org.esti.backend_esti.DTO.TeacherSubjectGroupDTO;
import org.esti.backend_esti.Entity.*;
import org.esti.backend_esti.Form.TeacherSubjectGroupForm;
import org.esti.backend_esti.Repository.GroupRepository;
import org.esti.backend_esti.Repository.SubjectRepository;
import org.esti.backend_esti.Repository.TeacherRepository;
import org.esti.backend_esti.Repository.TeacherSubjectGroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TeacherSubjectGroupService {

    private final TeacherSubjectGroupRepository teacherSubjectGroupRepository;
    private final GroupRepository groupRepository;
    private final TeacherRepository teacherRepository;
    private final SubjectRepository subjectRepository;


    @Autowired
    public TeacherSubjectGroupService(
            TeacherSubjectGroupRepository teacherSubjectGroupRepository,
            GroupRepository groupRepository,
            TeacherRepository teacherRepository,
            SubjectRepository subjectRepository
    ) {
        this.teacherSubjectGroupRepository = teacherSubjectGroupRepository;
        this.groupRepository = groupRepository;
        this.teacherRepository = teacherRepository;
        this.subjectRepository = subjectRepository;
    }

    public TeacherSubjectGroupDTO createTeacherSubjectGroup(TeacherSubjectGroupForm form) throws Exception {
        Group group = groupRepository.findById(form.getGroupId())
                .orElseThrow(() -> new Exception("Group not found with id: " + form.getGroupId()));
        Teacher teacher = teacherRepository.findById(form.getTeacherId())
                .orElseThrow(() -> new Exception("Teacher not found with id: " + form.getTeacherId()));
        Subject subject = subjectRepository.findById(form.getSubjectId())
                .orElseThrow(() -> new Exception("Subject not found with id: " + form.getSubjectId()));

        TeacherSubjectGroup teacherSubjectGroup = new TeacherSubjectGroup(form, group, teacher, subject);
        teacherSubjectGroup.setCreatedAt(LocalDateTime.now());
        teacherSubjectGroupRepository.save(teacherSubjectGroup);
        return TeacherSubjectGroupDTO.build(teacherSubjectGroup);
    }

    public TeacherSubjectGroupDTO updateTeacherSubjectGroup(TeacherSubjectGroupForm form, Long idTeacherSubjectGroup) throws Exception {
        validateIfTSGExists(idTeacherSubjectGroup);
        TeacherSubjectGroup teacherSubjectGroup = teacherSubjectGroupRepository.findById(idTeacherSubjectGroup).get();

        Group group = groupRepository.findById(form.getGroupId())
                .orElseThrow(() -> new Exception("Group not found with id: " + form.getGroupId()));
        Teacher teacher = teacherRepository.findById(form.getTeacherId())
                .orElseThrow(() -> new Exception("Teacher not found with id: " + form.getTeacherId()));
        Subject subject = subjectRepository.findById(form.getSubjectId())
                .orElseThrow(() -> new Exception("Subject not found with id: " + form.getSubjectId()));

        teacherSubjectGroup.updateFromForm(form, group, teacher, subject);
        teacherSubjectGroupRepository.save(teacherSubjectGroup);
        return TeacherSubjectGroupDTO.build(teacherSubjectGroup);
    }

    public void deleteTeacherSubjectGroup(Long idTeacherSubjectGroup) throws Exception {
        validateIfTSGExists(idTeacherSubjectGroup);
        teacherSubjectGroupRepository.deleteById(idTeacherSubjectGroup);
    }

    public void deleteTeacherSubjectGroupLogically(Long idTeacherSubjectGroup) throws Exception {
        TeacherSubjectGroup teacherSubjectGroup = teacherSubjectGroupRepository.findById(idTeacherSubjectGroup)
                .orElseThrow(() -> new Exception("TeacherSubjectGroup not found with id: " + idTeacherSubjectGroup));
        teacherSubjectGroup.markAsDeleted();
        teacherSubjectGroupRepository.save(teacherSubjectGroup);
    }

    public TeacherSubjectGroupDTO findById(Long idTeacherSubjectGroup) throws Exception {
        validateIfTSGExists(idTeacherSubjectGroup);
        TeacherSubjectGroup teacherSubjectGroup = teacherSubjectGroupRepository.findById(idTeacherSubjectGroup)
                .orElseThrow(() -> new Exception("TeacherSubjectGroup not found with id: " + idTeacherSubjectGroup));
        return TeacherSubjectGroupDTO.build(teacherSubjectGroup);
    }

    public List<TeacherSubjectGroupDTO> getAllTeacherSubjectGroup() {
        return teacherSubjectGroupRepository.findAll()
                .stream()
                .map(TeacherSubjectGroupDTO::build)
                .toList();
    }

    public List<TeacherSubjectGroup> getAllActiveTeacherSubjectGroups() {
        return teacherSubjectGroupRepository.findAllActive();
    }

    public List<TeacherSubjectGroupDTO> getByTeacher(Long teacherId) {
        return teacherSubjectGroupRepository.findByTeacherId(teacherId)
                .stream()
                .map(TeacherSubjectGroupDTO::build)
                .toList();
    }

    public List<TeacherSubjectGroupDTO> getBySubject(Long subjectId) {
        return teacherSubjectGroupRepository.findBySubjectId(subjectId)
                .stream()
                .map(TeacherSubjectGroupDTO::build)
                .toList();
    }

    public List<TeacherSubjectGroupDTO> getByGroup(Long groupId) {
        return teacherSubjectGroupRepository.findByGroupId(groupId)
                .stream()
                .map(TeacherSubjectGroupDTO::build)
                .toList();
    }

    public List<TeacherSubjectGroup> getByTeacherAndGroup(Long teacherId, Long groupId) {
        return teacherSubjectGroupRepository.findByTeacherIdAndGroupId(teacherId, groupId);
    }

    public List<TeacherSubjectGroup> getByTeacherAndSubject(Long teacherId, Long subjectId) {
        return teacherSubjectGroupRepository.findByTeacherIdAndSubjectId(teacherId, subjectId);
    }

    public TeacherSubjectGroup getByGroupAndSubject(Long groupId, Long subjectId) {
        return teacherSubjectGroupRepository.findByGroupIdAndSubjectId(groupId, subjectId);
    }

    public void validateIfTSGExists(Long idTeacherSubjectGroup) throws Exception {
        if (!teacherSubjectGroupRepository.existsById(idTeacherSubjectGroup)) {
            throw new Exception("TeacherSubjectGroup not found with id: " + idTeacherSubjectGroup);
        }
    }
}
