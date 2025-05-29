package org.esti.backend_esti.Service;

import org.esti.backend_esti.DTO.CardexDTO;
import org.esti.backend_esti.Entity.*;
import org.esti.backend_esti.Form.CardexForm;
import org.esti.backend_esti.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CardexService {

    private final CardexRepository cardexRepository;
    private final GroupRepository groupRepository;
    private final TeacherRepository teacherRepository;
    private final StudentRepository studentRepository;
    private final SubjectRepository subjectRepository;

    @Autowired
    public CardexService(
            CardexRepository cardexRepository,
            GroupRepository groupRepository,
            TeacherRepository teacherRepository,
            StudentRepository studentRepository,
            SubjectRepository subjectRepository
    ) {
        this.cardexRepository = cardexRepository;
        this.groupRepository = groupRepository;
        this.teacherRepository = teacherRepository;
        this.studentRepository = studentRepository;
        this.subjectRepository = subjectRepository;
    }

    public CardexDTO createCardex(CardexForm form) throws Exception {
        Group group = groupRepository.findById(form.getGroupId())
                .orElseThrow(() -> new Exception("Group not found with id: " + form.getGroupId()));
        Teacher teacher = teacherRepository.findById(form.getTeacherId())
                .orElseThrow(() -> new Exception("Teacher not found with id: " + form.getTeacherId()));
        Student student = studentRepository.findById(form.getStudentId())
                .orElseThrow(() -> new Exception("Student not found with id: " + form.getStudentId()));
        Subject subject = subjectRepository.findById(form.getSubjectId())
                .orElseThrow(() -> new Exception("Subject not found with id: " + form.getSubjectId()));

        Cardex cardex = new Cardex(form, group, teacher, student, subject);
        cardexRepository.save(cardex);
        return CardexDTO.build(cardex);
    }

    public CardexDTO updateCardex(CardexForm form, Long idCardex) throws Exception {
        validateIfCardexExists(idCardex);
        Cardex cardex = cardexRepository.findById(idCardex).get();

        Group group = groupRepository.findById(form.getGroupId())
                .orElseThrow(() -> new Exception("Group not found with id: " + form.getGroupId()));
        Teacher teacher = teacherRepository.findById(form.getTeacherId())
                .orElseThrow(() -> new Exception("Teacher not found with id: " + form.getTeacherId()));
        Student student = studentRepository.findById(form.getStudentId())
                .orElseThrow(() -> new Exception("Student not found with id: " + form.getStudentId()));
        Subject subject = subjectRepository.findById(form.getSubjectId())
                .orElseThrow(() -> new Exception("Subject not found with id: " + form.getSubjectId()));

        cardex.updateFromForm(form, group, teacher, student, subject);
        cardexRepository.save(cardex);
        return CardexDTO.build(cardex);
    }

    public void deleteCardex(Long idCardex) throws Exception {
        validateIfCardexExists(idCardex);
        cardexRepository.deleteById(idCardex);
    }

    public void deleteCardexLogically(Long idCardex) throws Exception {
        Cardex cardex = cardexRepository.findById(idCardex)
                .orElseThrow(() -> new Exception("Cardex not found with id: " + idCardex));
        cardex.markAsDeleted();
        cardexRepository.save(cardex);
    }

    public CardexDTO findById(Long idCardex) throws Exception {
        validateIfCardexExists(idCardex);
        Cardex cardex = cardexRepository.findById(idCardex)
                .orElseThrow(() -> new Exception("Cardex not found with id: " + idCardex));
        return CardexDTO.build(cardex);
    }

    public List<CardexDTO> getAllCardex() {
        return cardexRepository.findAll()
                .stream()
                .map(CardexDTO::build)
                .toList();
    }

    public List<CardexDTO> getByGroup(Long groupId) {
        return cardexRepository.findByGroupId(groupId)
                .stream()
                .map(CardexDTO::build)
                .toList();
    }

    public List<CardexDTO> getByStudent(Long studentId) {
        return cardexRepository.findByStudentId(studentId)
                .stream()
                .map(CardexDTO::build)
                .toList();
    }

    public List<CardexDTO> getByTeacher(Long teacherId) {
        return cardexRepository.findByTeacherId(teacherId)
                .stream()
                .map(CardexDTO::build)
                .toList();
    }

    public List<CardexDTO> getBySubject(Long subjectId) {
        return cardexRepository.findBySubjectId(subjectId)
                .stream()
                .map(CardexDTO::build)
                .toList();
    }

    public void validateIfCardexExists(Long idCardex) throws Exception {
        if (!cardexRepository.existsById(idCardex)) {
            throw new Exception("Cardex not found with id: " + idCardex);
        }
    }
}
