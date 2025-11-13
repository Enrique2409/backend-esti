package org.esti.backend_esti.integration;

import org.esti.backend_esti.DTO.CardexDTO;
import org.esti.backend_esti.Entity.*;
import org.esti.backend_esti.Form.CardexForm;
import org.esti.backend_esti.Repository.*;
import org.esti.backend_esti.Service.CardexService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
@AutoConfigureMockMvc(addFilters = false)
public class CardexIntegrationTest {

    @Autowired
    private CardexService cardexService;

    @Autowired
    private CardexRepository cardexRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private TeacherSubjectRepository teacherSubjectRepository;

    @Autowired
    private PeriodRepository periodRepository;

    @Autowired
    private GroupRepository groupRepository;

    private Student testStudent;
    private TeacherSubject testTeacherSubject;
    private Period testPeriod;

    @BeforeEach
    void setUp() {
        cardexRepository.deleteAll();
        teacherSubjectRepository.deleteAll();
        studentRepository.deleteAll();
        teacherRepository.deleteAll();
        subjectRepository.deleteAll();
        periodRepository.deleteAll();
        groupRepository.deleteAll();

        Group group = new Group();
        group.setGroupName("3-A");
        group.setGrade(3);
        group = groupRepository.save(group);

        testStudent = new Student();
        testStudent.setName("Juan");
        testStudent.setLastNamePaternal("Pérez");
        testStudent.setLastNameMaternal("López");
        testStudent.setCurp("PERJ000912HVZFSDA7");
        testStudent.setBirthDate(LocalDate.parse("2000-12-12"));
        testStudent.setGroup(group);
        testStudent = studentRepository.save(testStudent);

        Teacher teacher = new Teacher();
        teacher.setName("María");
        teacher.setLastName("González");
        teacher.setEmail("maria@test.com");
        teacher.setPassword("password123");
        teacher.setRole(Role.TEACHER);
        teacher = teacherRepository.save(teacher);

        Subject subject = new Subject();
        subject.setName("Matemáticas");
        subject = subjectRepository.save(subject);

        testTeacherSubject = new TeacherSubject();
        testTeacherSubject.setTeacher(teacher);
        testTeacherSubject.setSubject(subject);
        testTeacherSubject = teacherSubjectRepository.save(testTeacherSubject);

        testPeriod = new Period();
        testPeriod.setCve("2024-2025");
        testPeriod.setDescription("yacasi");
        testPeriod = periodRepository.save(testPeriod);
    }

    @Test
    void shouldCreateCardexSuccessfully() throws Exception {
        CardexForm form = new CardexForm();
        form.setStudentId(testStudent.getIdStudent());
        form.setTeacherSubjectId(testTeacherSubject.getIdTeacherSubject());
        form.setPeriodId(testPeriod.getIdPeriod());
        form.setFirstPartial(95);
        form.setSecondPartial(90);
        form.setThirdPartial(88);
        form.setFinalGrade(91);

        CardexDTO result = cardexService.createCardex(form);

        assertNotNull(result);
        assertEquals(testStudent.getIdStudent(), result.getIdStudent());
        assertEquals(95, result.getFirstPartial());
        assertEquals(90, result.getSecondPartial());
        assertEquals(88, result.getThirdPartial());
        assertEquals(91, result.getFinalGrade());

        List<Cardex> cardexes = cardexRepository.findAll();
        assertEquals(1, cardexes.size());
    }

    @Test
    void shouldThrowExceptionWhenStudentNotFound() {
        CardexForm form = new CardexForm();
        form.setStudentId(999L); // ID inexistente
        form.setTeacherSubjectId(testTeacherSubject.getIdTeacherSubject());
        form.setPeriodId(testPeriod.getIdPeriod());
        form.setFirstPartial(90);
        form.setSecondPartial(85);
        form.setThirdPartial(88);
        form.setFinalGrade(87);

        Exception exception = assertThrows(Exception.class,
                () -> cardexService.createCardex(form));
        assertTrue(exception.getMessage().contains("Student not found"));

        List<Cardex> cardexes = cardexRepository.findAll();
        assertEquals(0, cardexes.size());
    }

    @Test
    void shouldFindCardexById() throws Exception {
        CardexForm form = new CardexForm();
        form.setStudentId(testStudent.getIdStudent());
        form.setTeacherSubjectId(testTeacherSubject.getIdTeacherSubject());
        form.setPeriodId(testPeriod.getIdPeriod());
        form.setFirstPartial(95);
        form.setSecondPartial(90);
        form.setThirdPartial(88);
        form.setFinalGrade(91);

        CardexDTO created = cardexService.createCardex(form);

        CardexDTO found = cardexService.findById(created.getIdCardex());

        assertNotNull(found);
        assertEquals(created.getIdCardex(), found.getIdCardex());
        assertEquals(95, found.getFirstPartial());
    }

    @Test
    void shouldUpdateCardexSuccessfully() throws Exception {
        CardexForm createForm = new CardexForm();
        createForm.setStudentId(testStudent.getIdStudent());
        createForm.setTeacherSubjectId(testTeacherSubject.getIdTeacherSubject());
        createForm.setPeriodId(testPeriod.getIdPeriod());
        createForm.setFirstPartial(80);
        createForm.setSecondPartial(85);
        createForm.setThirdPartial(90);
        createForm.setFinalGrade(85);

        CardexDTO created = cardexService.createCardex(createForm);

        CardexForm updateForm = new CardexForm();
        updateForm.setStudentId(testStudent.getIdStudent());
        updateForm.setTeacherSubjectId(testTeacherSubject.getIdTeacherSubject());
        updateForm.setPeriodId(testPeriod.getIdPeriod());
        updateForm.setFirstPartial(95);
        updateForm.setSecondPartial(92);
        updateForm.setThirdPartial(94);
        updateForm.setFinalGrade(93);

        CardexDTO updated = cardexService.updateCardex(updateForm, created.getIdCardex());

        assertNotNull(updated);
        assertEquals(created.getIdCardex(), updated.getIdCardex());
        assertEquals(95, updated.getFirstPartial());
        assertEquals(92, updated.getSecondPartial());
        assertEquals(94, updated.getThirdPartial());
        assertEquals(93, updated.getFinalGrade());
    }

    @Test
    void shouldDeleteCardexSuccessfully() throws Exception {
        CardexForm form = new CardexForm();
        form.setStudentId(testStudent.getIdStudent());
        form.setTeacherSubjectId(testTeacherSubject.getIdTeacherSubject());
        form.setPeriodId(testPeriod.getIdPeriod());
        form.setFirstPartial(95);
        form.setSecondPartial(90);
        form.setThirdPartial(88);
        form.setFinalGrade(91);

        CardexDTO created = cardexService.createCardex(form);

        cardexService.deleteCardex(created.getIdCardex());

        List<Cardex> cardexes = cardexRepository.findAll();
        assertEquals(0, cardexes.size());
    }

    @Test
    void shouldGetCardexByStudent() throws Exception {
        CardexForm form1 = new CardexForm();
        form1.setStudentId(testStudent.getIdStudent());
        form1.setTeacherSubjectId(testTeacherSubject.getIdTeacherSubject());
        form1.setPeriodId(testPeriod.getIdPeriod());
        form1.setFirstPartial(95);
        form1.setSecondPartial(90);
        form1.setThirdPartial(88);
        form1.setFinalGrade(91);

        cardexService.createCardex(form1);

        List<CardexDTO> cardexes = cardexService.getByStudent(testStudent.getIdStudent());

        assertNotNull(cardexes);
        assertFalse(cardexes.isEmpty());
        assertEquals(1, cardexes.size());
        assertEquals(testStudent.getIdStudent(), cardexes.get(0).getIdStudent());
    }

    @Test
    void shouldGetAllCardex() throws Exception {
        CardexForm form1 = new CardexForm();
        form1.setStudentId(testStudent.getIdStudent());
        form1.setTeacherSubjectId(testTeacherSubject.getIdTeacherSubject());
        form1.setPeriodId(testPeriod.getIdPeriod());
        form1.setFirstPartial(95);
        form1.setSecondPartial(90);
        form1.setThirdPartial(88);
        form1.setFinalGrade(91);

        cardexService.createCardex(form1);
        cardexService.createCardex(form1);

        List<CardexDTO> allCardex = cardexService.getAllCardex();

        assertNotNull(allCardex);
        assertEquals(2, allCardex.size());
    }
}