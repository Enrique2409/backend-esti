package org.esti.backend_esti.service;

import org.esti.backend_esti.DTO.CardexDTO;
import org.esti.backend_esti.Entity.*;
import org.esti.backend_esti.Form.CardexForm;
import org.esti.backend_esti.Repository.CardexRepository;
import org.esti.backend_esti.Repository.PeriodRepository;
import org.esti.backend_esti.Repository.StudentRepository;
import org.esti.backend_esti.Repository.TeacherSubjectRepository;
import org.esti.backend_esti.Service.CardexService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class CardexServiceTest {

    @Mock
    private CardexRepository cardexRepository;

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private TeacherSubjectRepository teacherSubjectRepository;

    @Mock
    private PeriodRepository periodRepository;

    @InjectMocks
    private CardexService cardexService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createCardexSuccess() throws Exception {
        CardexForm form = new CardexForm();
        form.setStudentId(1L);
        form.setTeacherSubjectId(1L);
        form.setPeriodId(1L);
        form.setFirstPartial(95);
        form.setSecondPartial(90);
        form.setThirdPartial(88);
        form.setFinalGrade(91);

        Group group = new Group();
        group.setIdGroup(1L);
        group.setGroupName("3-A");

        Student student = new Student();
        student.setIdStudent(1L);
        student.setName("Juan");
        student.setLastNamePaternal("Pérez");
        student.setGroup(group);

        Teacher teacher = new Teacher();
        teacher.setIdTeacher(1L);
        teacher.setName("María");
        teacher.setLastName("González");

        Subject subject = new Subject();
        subject.setIdSubject(1L);
        subject.setName("Matemáticas");

        TeacherSubject teacherSubject = new TeacherSubject();
        teacherSubject.setIdTeacherSubject(1L);
        teacherSubject.setTeacher(teacher);
        teacherSubject.setSubject(subject);

        Period period = new Period();
        period.setIdPeriod(1L);
        period.setCve("2024-2025");

        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));
        when(teacherSubjectRepository.findById(1L)).thenReturn(Optional.of(teacherSubject));
        when(periodRepository.findById(1L)).thenReturn(Optional.of(period));

        when(cardexRepository.save(any(Cardex.class))).thenAnswer(invocation -> {
            Cardex c = invocation.getArgument(0);
            c.setIdCardex(100L);
            return c;
        });

        CardexDTO result = cardexService.createCardex(form);

        assertNotNull(result);
        verify(cardexRepository, times(1)).save(any(Cardex.class));
        verify(studentRepository, times(1)).findById(1L);
        verify(teacherSubjectRepository, times(1)).findById(1L);
        verify(periodRepository, times(1)).findById(1L);
    }

    @Test
    void createCardexStudentNotFound() {
        CardexForm form = new CardexForm();
        form.setStudentId(99L);
        form.setTeacherSubjectId(1L);
        form.setPeriodId(1L);
        form.setFirstPartial(90);
        form.setSecondPartial(80);
        form.setThirdPartial(70);
        form.setFinalGrade(80);

        when(studentRepository.findById(99L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(Exception.class, () -> cardexService.createCardex(form));
        assertEquals("Student not found with id: 99", exception.getMessage());

        verify(cardexRepository, never()).save(any(Cardex.class));
    }

    @Test
    void createCardexTeacherSubjectNotFound() {
        CardexForm form = new CardexForm();
        form.setStudentId(1L);
        form.setTeacherSubjectId(99L);
        form.setPeriodId(1L);
        form.setFirstPartial(90);
        form.setSecondPartial(80);
        form.setThirdPartial(70);
        form.setFinalGrade(80);

        Group group = new Group();
        group.setIdGroup(1L);

        Student student = new Student();
        student.setIdStudent(1L);
        student.setGroup(group);

        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));
        when(teacherSubjectRepository.findById(99L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(Exception.class, () -> cardexService.createCardex(form));
        assertEquals("TeacherSubject not found with id: 99", exception.getMessage());

        verify(cardexRepository, never()).save(any(Cardex.class));
    }

    @Test
    void createCardexPeriodNotFound() {
        CardexForm form = new CardexForm();
        form.setStudentId(1L);
        form.setTeacherSubjectId(1L);
        form.setPeriodId(99L);
        form.setFirstPartial(90);
        form.setSecondPartial(80);
        form.setThirdPartial(70);
        form.setFinalGrade(80);

        Group group = new Group();
        group.setIdGroup(1L);

        Student student = new Student();
        student.setIdStudent(1L);
        student.setGroup(group);

        Teacher teacher = new Teacher();
        teacher.setIdTeacher(1L);

        Subject subject = new Subject();
        subject.setIdSubject(1L);

        TeacherSubject teacherSubject = new TeacherSubject();
        teacherSubject.setIdTeacherSubject(1L);
        teacherSubject.setTeacher(teacher);
        teacherSubject.setSubject(subject);

        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));
        when(teacherSubjectRepository.findById(1L)).thenReturn(Optional.of(teacherSubject));
        when(periodRepository.findById(99L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(Exception.class, () -> cardexService.createCardex(form));
        assertEquals("Period not found with id: 99", exception.getMessage());

        verify(cardexRepository, never()).save(any(Cardex.class));
    }

    @Test
    void getCardexByIdSuccess() throws Exception {
        // Arrange
        Long cardexId = 10L;

        Group group = new Group();
        group.setIdGroup(1L);

        Student student = new Student();
        student.setIdStudent(1L);
        student.setGroup(group);

        Teacher teacher = new Teacher();
        teacher.setIdTeacher(1L);

        Subject subject = new Subject();
        subject.setIdSubject(1L);

        TeacherSubject teacherSubject = new TeacherSubject();
        teacherSubject.setIdTeacherSubject(1L);
        teacherSubject.setTeacher(teacher);
        teacherSubject.setSubject(subject);

        Period period = new Period();
        period.setIdPeriod(1L);

        Cardex cardex = new Cardex();
        cardex.setIdCardex(cardexId);
        cardex.setStudent(student);
        cardex.setTeacherSubject(teacherSubject);
        cardex.setPeriod(period);
        cardex.setFirstPartial(90);
        cardex.setSecondPartial(85);
        cardex.setThirdPartial(88);
        cardex.setFinalGrade(87);

        when(cardexRepository.existsById(cardexId)).thenReturn(true);

        when(cardexRepository.findById(cardexId)).thenReturn(Optional.of(cardex));

        CardexDTO result = cardexService.findById(cardexId);

        assertNotNull(result);
        assertEquals(cardexId, result.getIdCardex());
        verify(cardexRepository, times(1)).existsById(cardexId);
        verify(cardexRepository, times(1)).findById(cardexId);
    }

    @Test
    void getCardexByIdNotFound() {
        Long cardexId = 99L;

        when(cardexRepository.existsById(cardexId)).thenReturn(false);

        Exception exception = assertThrows(Exception.class, () -> cardexService.findById(cardexId));
        assertEquals("Cardex not found with id: 99", exception.getMessage());

        verify(cardexRepository, times(1)).existsById(cardexId);
        verify(cardexRepository, never()).findById(cardexId);
    }

    @Test
    void deleteCardexSuccess() throws Exception {
        Long cardexId = 10L;

        when(cardexRepository.existsById(cardexId)).thenReturn(true);
        doNothing().when(cardexRepository).deleteById(cardexId);

        cardexService.deleteCardex(cardexId);

        verify(cardexRepository, times(1)).existsById(cardexId);
        verify(cardexRepository, times(1)).deleteById(cardexId);
    }

    @Test
    void deleteCardexNotFound() {
        Long cardexId = 99L;

        when(cardexRepository.existsById(cardexId)).thenReturn(false);

        Exception exception = assertThrows(Exception.class, () -> cardexService.deleteCardex(cardexId));
        assertEquals("Cardex not found with id: 99", exception.getMessage());

        verify(cardexRepository, times(1)).existsById(cardexId);
        verify(cardexRepository, never()).deleteById(cardexId);
    }

    @Test
    void updateCardexSuccess() throws Exception {
        Long cardexId = 10L;

        CardexForm form = new CardexForm();
        form.setStudentId(1L);
        form.setTeacherSubjectId(1L);
        form.setPeriodId(1L);
        form.setFirstPartial(95);
        form.setSecondPartial(90);
        form.setThirdPartial(88);
        form.setFinalGrade(91);

        Group group = new Group();
        group.setIdGroup(1L);

        Student student = new Student();
        student.setIdStudent(1L);
        student.setGroup(group);

        Teacher teacher = new Teacher();
        teacher.setIdTeacher(1L);

        Subject subject = new Subject();
        subject.setIdSubject(1L);

        TeacherSubject teacherSubject = new TeacherSubject();
        teacherSubject.setIdTeacherSubject(1L);
        teacherSubject.setTeacher(teacher);
        teacherSubject.setSubject(subject);

        Period period = new Period();
        period.setIdPeriod(1L);

        Cardex existingCardex = new Cardex();
        existingCardex.setIdCardex(cardexId);
        existingCardex.setStudent(student);
        existingCardex.setTeacherSubject(teacherSubject);
        existingCardex.setPeriod(period);

        when(cardexRepository.existsById(cardexId)).thenReturn(true);
        when(cardexRepository.findById(cardexId)).thenReturn(Optional.of(existingCardex));
        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));
        when(teacherSubjectRepository.findById(1L)).thenReturn(Optional.of(teacherSubject));
        when(periodRepository.findById(1L)).thenReturn(Optional.of(period));
        when(cardexRepository.save(any(Cardex.class))).thenReturn(existingCardex);

        CardexDTO result = cardexService.updateCardex(form, cardexId);

        assertNotNull(result);
        verify(cardexRepository, times(1)).existsById(cardexId);
        verify(cardexRepository, times(1)).findById(cardexId);
        verify(cardexRepository, times(1)).save(any(Cardex.class));
    }
}