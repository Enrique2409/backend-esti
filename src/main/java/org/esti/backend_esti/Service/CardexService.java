package org.esti.backend_esti.Service;

import org.esti.backend_esti.DTO.CardexDTO;
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
    private final TeacherSubjectRepository teacherSubjectRepository;
    private final PeriodRepository periodRepository;

    @Autowired
    public CardexService(
            CardexRepository cardexRepository,
            StudentRepository studentRepository,
            TeacherSubjectRepository teacherSubjectRepository,
            PeriodRepository periodRepository
    ) {
        this.cardexRepository = cardexRepository;
        this.studentRepository = studentRepository;
        this.teacherSubjectRepository = teacherSubjectRepository;
        this.periodRepository = periodRepository;
    }


    public CardexDTO createCardex(CardexForm form) throws Exception {
        Student student = studentRepository.findById(form.getStudentId())
                .orElseThrow(() -> new Exception("Student not found with id: " + form.getStudentId()));
        TeacherSubject teacherSubject = teacherSubjectRepository.findById(form.getTeacherSubjectId())
                .orElseThrow(() -> new Exception("TeacherSubject not found with id: " + form.getTeacherSubjectId()));
        Period period = periodRepository.findById(form.getPeriodId())
                .orElseThrow(() -> new Exception("Period not found with id: " + form.getPeriodId()));

        Cardex cardex = new Cardex(form, student, teacherSubject, period);
        cardexRepository.save(cardex);
        cardex.setCreatedAt(LocalDateTime.now());
        return CardexDTO.build(cardex);
    }

    public CardexDTO updateCardex(CardexForm form, Long idCardex) throws Exception {
        validateIfCardexExists(idCardex);
        Cardex cardex = cardexRepository.findById(idCardex).get();

        Student student = studentRepository.findById(form.getStudentId())
                .orElseThrow(() -> new Exception("Student not found with id: " + form.getStudentId()));
        TeacherSubject teacherSubject = teacherSubjectRepository.findById(form.getTeacherSubjectId())
                .orElseThrow(() -> new Exception("TeacherSubject not found with id: " + form.getTeacherSubjectId()));
        Period period = periodRepository.findById(form.getPeriodId())
                .orElseThrow(() -> new Exception("Period not found with id: " + form.getPeriodId()));

        cardex.updateFromForm(form, student, teacherSubject, period);
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
/*
    public Page<CardexDTO> searchStudentsByTeacher(
            Long teacherId,
            String subjectName,
            String groupName,
            Integer grade,
            String keyword,
            int page,
            int size) {

        PageRequest pageable = PageRequest.of(page, size);

        Page<Cardex> cardexPage = cardexRepository.findByTeacherWithFilters(
                teacherId,
                subjectName,
                groupName,
                grade,
                keyword,
                pageable
        );

        return cardexPage.map(CardexDTO::build);
    }
*/

    public Page<CardexDTO> searchStudentsByTeacher(
            Long teacherId,
            Long subjectId,
            String groupName,
            Integer grade,
            String keyword,
            int page,
            int size) {

        PageRequest pageable = PageRequest.of(page, size);

        Page<Cardex> cardexPage = cardexRepository.findByTeacherWithFilters(
                teacherId,
                subjectId,
                groupName,
                grade,
                keyword,
                pageable
        );

        return cardexPage.map(CardexDTO::build);
    }

}
