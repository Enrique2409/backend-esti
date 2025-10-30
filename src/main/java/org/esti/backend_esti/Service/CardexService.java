package org.esti.backend_esti.Service;

import org.esti.backend_esti.DTO.CardexDTO;
import org.esti.backend_esti.DTO.PeriodDTO;
import org.esti.backend_esti.Entity.*;
import org.esti.backend_esti.Form.CardexForm;
import org.esti.backend_esti.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CardexService {

    private final CardexRepository cardexRepository;
    private final StudentRepository studentRepository;
    private final TeacherSubjectGroupRepository teacherSubjectGroupRepository;

    @Autowired
    public CardexService(
            CardexRepository cardexRepository,
            StudentRepository studentRepository,
            TeacherSubjectGroupRepository teacherSubjectGroupRepository
    ) {
        this.cardexRepository = cardexRepository;
        this.studentRepository = studentRepository;
        this.teacherSubjectGroupRepository = teacherSubjectGroupRepository;
    }


    public CardexDTO createCardex(CardexForm form) throws Exception {
        Student student = studentRepository.findById(form.getStudentId())
                .orElseThrow(() -> new Exception("Student not found with id: " + form.getStudentId()));
        TeacherSubjectGroup teacherSubjectGroup = teacherSubjectGroupRepository.findById(form.getTeacherSubjectGroupId())
                .orElseThrow(() -> new Exception("TeacherSubjectGroup not found with id: " + form.getTeacherSubjectGroupId()));

        Cardex cardex = new Cardex(form, student, teacherSubjectGroup);
        cardexRepository.save(cardex);
        cardex.setCreatedAt(LocalDateTime.now());
        return CardexDTO.build(cardex);
    }

    public CardexDTO updateCardex(CardexForm form, Long idCardex) throws Exception {
        validateIfCardexExists(idCardex);
        Cardex cardex = cardexRepository.findById(idCardex).get();

        Student student = studentRepository.findById(form.getStudentId())
                .orElseThrow(() -> new Exception("Student not found with id: " + form.getStudentId()));
        TeacherSubjectGroup teacherSubjectGroup = teacherSubjectGroupRepository.findById(form.getTeacherSubjectGroupId())
                .orElseThrow(() -> new Exception("TeacherSubjectGroup not found with id: " + form.getTeacherSubjectGroupId()));

        cardex.updateFromForm(form, student, teacherSubjectGroup);
        cardexRepository.save(cardex);
        cardex.setUpdatedAt(LocalDateTime.now());
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

    public Page<CardexDTO> getCardex (int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Cardex> cardexPage = cardexRepository.findAll(pageable);
        return cardexPage.map(CardexDTO::build);
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

    public List<CardexDTO> getByTeacherAndGroup(Long teacherId, Long groupId) {
        return cardexRepository.findByTeacherAndGroup(teacherId, groupId)
                .stream()
                .map(CardexDTO::build)
                .toList();
    }

    public void validateIfCardexExists(Long idCardex) throws Exception {
        if (!cardexRepository.existsById(idCardex)) {
            throw new Exception("Cardex not found with id: " + idCardex);
        }
    }

    public Page<CardexDTO> searchCardex (String keyword, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page <Cardex> cardexPage = cardexRepository.searchCardex(keyword, pageable);
        return cardexPage.map(CardexDTO::build);
    }
}
