package org.esti.backend_esti.system;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.esti.backend_esti.Entity.*;
import org.esti.backend_esti.Form.CardexForm;
import org.esti.backend_esti.Repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
public class CardexSystemTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

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
    private Teacher testTeacher;
    private Subject testSubject;
    private TeacherSubject testTeacherSubject;
    private Period testPeriod;
    private Group testGroup;

    @BeforeEach
    void setUp() {
        cardexRepository.deleteAll();
        teacherSubjectRepository.deleteAll();
        studentRepository.deleteAll();
        teacherRepository.deleteAll();
        subjectRepository.deleteAll();
        periodRepository.deleteAll();
        groupRepository.deleteAll();

        testGroup = new Group();
        testGroup.setGroupName("3-A");
        testGroup.setGrade(3);
        testGroup = groupRepository.save(testGroup);

        testStudent = new Student();
        testStudent.setName("Juan");
        testStudent.setLastNamePaternal("Pérez");
        testStudent.setLastNameMaternal("López");
        testStudent.setCurp("PERJ000912HVZFSDA7");
        testStudent.setBirthDate(LocalDate.parse("2000-12-12"));
        testStudent.setGroup(testGroup);
        testStudent = studentRepository.save(testStudent);

        testTeacher = new Teacher();
        testTeacher.setName("María");
        testTeacher.setLastName("González");
        testTeacher.setEmail("maria@test.com");
        testTeacher.setPassword("password123");
        testTeacher.setRole(Role.TEACHER);
        testTeacher = teacherRepository.save(testTeacher);

        testSubject = new Subject();
        testSubject.setName("Matemáticas");
        testSubject = subjectRepository.save(testSubject);

        testTeacherSubject = new TeacherSubject();
        testTeacherSubject.setTeacher(testTeacher);
        testTeacherSubject.setSubject(testSubject);
        testTeacherSubject = teacherSubjectRepository.save(testTeacherSubject);

        testPeriod = new Period();
        testPeriod.setCve("2024-2025");
        testPeriod.setDescription("yacasi");
        testPeriod = periodRepository.save(testPeriod);
    }

    @Test
    void shouldCreateCardexViaRestAPI() throws Exception {
        // Arrange
        CardexForm form = new CardexForm();
        form.setStudentId(testStudent.getIdStudent());
        form.setTeacherSubjectId(testTeacherSubject.getIdTeacherSubject());
        form.setPeriodId(testPeriod.getIdPeriod());
        form.setFirstPartial(95);
        form.setSecondPartial(90);
        form.setThirdPartial(88);
        form.setFinalGrade(91);

        // Act & Assert - Usando los nombres correctos del DTO
        mockMvc.perform(post("/esti/cardex/create-cardex")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(form)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idCardex").exists())
                .andExpect(jsonPath("$.idStudent").value(testStudent.getIdStudent()))
                .andExpect(jsonPath("$.studentName").value("Juan"))
                .andExpect(jsonPath("$.studentLastNamePaternal").value("Pérez"))
                .andExpect(jsonPath("$.studentLastNameMaternal").value("López"))
                .andExpect(jsonPath("$.idTeacher").value(testTeacher.getIdTeacher()))
                .andExpect(jsonPath("$.teacherName").value("María"))
                .andExpect(jsonPath("$.teacherLastName").value("González"))
                .andExpect(jsonPath("$.idSubject").value(testSubject.getIdSubject()))
                .andExpect(jsonPath("$.subjectName").value("Matemáticas"))
                .andExpect(jsonPath("$.idGroup").value(testGroup.getIdGroup()))
                .andExpect(jsonPath("$.groupName").value("3-A"))
                .andExpect(jsonPath("$.grade").value(3))
                .andExpect(jsonPath("$.idPeriod").value(testPeriod.getIdPeriod()))
                .andExpect(jsonPath("$.period").value("2024-2025"))
                .andExpect(jsonPath("$.firstPartial").value(95))
                .andExpect(jsonPath("$.secondPartial").value(90))
                .andExpect(jsonPath("$.thirdPartial").value(88))
                .andExpect(jsonPath("$.finalGrade").value(91))
                .andExpect(jsonPath("$.createdAt").exists())
                .andExpect(jsonPath("$.deletedAt").doesNotExist());
    }

    @Test
    void shouldReturnBadRequestWhenCreatingCardexWithInvalidData() throws Exception {
        CardexForm form = new CardexForm();
        form.setStudentId(999L);
        form.setTeacherSubjectId(testTeacherSubject.getIdTeacherSubject());
        form.setPeriodId(testPeriod.getIdPeriod());
        form.setFirstPartial(95);
        form.setSecondPartial(90);
        form.setThirdPartial(88);
        form.setFinalGrade(91);

        mockMvc.perform(post("/esti/cardex/create-cardex")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(form)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value(containsString("not found")));
    }

    @Test
    void shouldGetCardexById() throws Exception {
        Cardex cardex = new Cardex();
        cardex.setStudent(testStudent);
        cardex.setTeacherSubject(testTeacherSubject);
        cardex.setPeriod(testPeriod);
        cardex.setFirstPartial(95);
        cardex.setSecondPartial(90);
        cardex.setThirdPartial(88);
        cardex.setFinalGrade(91);
        cardex = cardexRepository.save(cardex);

        mockMvc.perform(get("/esti/cardex/{cardexId}", cardex.getIdCardex()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idCardex").value(cardex.getIdCardex()))
                .andExpect(jsonPath("$.idStudent").value(testStudent.getIdStudent()))
                .andExpect(jsonPath("$.studentName").value("Juan"))
                .andExpect(jsonPath("$.firstPartial").value(95))
                .andExpect(jsonPath("$.secondPartial").value(90))
                .andExpect(jsonPath("$.thirdPartial").value(88))
                .andExpect(jsonPath("$.finalGrade").value(91));
    }

    @Test
    void shouldReturnNotFoundWhenCardexNotFound() throws Exception {
        // Act & Assert - Con GlobalExceptionHandler debe dar 404
        mockMvc.perform(get("/esti/cardex/{cardexId}", 999L))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value(containsString("not found")));
    }

    @Test
    void shouldGetAllCardex() throws Exception {
        // Arrange - Crear varios cardex
        Cardex cardex1 = new Cardex();
        cardex1.setStudent(testStudent);
        cardex1.setTeacherSubject(testTeacherSubject);
        cardex1.setPeriod(testPeriod);
        cardex1.setFirstPartial(95);
        cardex1.setSecondPartial(90);
        cardex1.setThirdPartial(88);
        cardex1.setFinalGrade(91);
        cardexRepository.save(cardex1);

        Cardex cardex2 = new Cardex();
        cardex2.setStudent(testStudent);
        cardex2.setTeacherSubject(testTeacherSubject);
        cardex2.setPeriod(testPeriod);
        cardex2.setFirstPartial(85);
        cardex2.setSecondPartial(88);
        cardex2.setThirdPartial(90);
        cardex2.setFinalGrade(87);
        cardexRepository.save(cardex2);

        // Act & Assert
        mockMvc.perform(get("/esti/cardex/getAll"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].idCardex").exists())
                .andExpect(jsonPath("$[0].idStudent").value(testStudent.getIdStudent()))
                .andExpect(jsonPath("$[0].firstPartial").exists())
                .andExpect(jsonPath("$[1].idStudent").value(testStudent.getIdStudent()))
                .andExpect(jsonPath("$[1].firstPartial").exists());
    }

    @Test
    void shouldUpdateCardexViaRestAPI() throws Exception {
        // Arrange - Crear un cardex primero
        Cardex cardex = new Cardex();
        cardex.setStudent(testStudent);
        cardex.setTeacherSubject(testTeacherSubject);
        cardex.setPeriod(testPeriod);
        cardex.setFirstPartial(80);
        cardex.setSecondPartial(85);
        cardex.setThirdPartial(90);
        cardex.setFinalGrade(85);
        cardex = cardexRepository.save(cardex);

        // Preparar actualización
        CardexForm updateForm = new CardexForm();
        updateForm.setStudentId(testStudent.getIdStudent());
        updateForm.setTeacherSubjectId(testTeacherSubject.getIdTeacherSubject());
        updateForm.setPeriodId(testPeriod.getIdPeriod());
        updateForm.setFirstPartial(95);
        updateForm.setSecondPartial(92);
        updateForm.setThirdPartial(94);
        updateForm.setFinalGrade(93);

        // Act & Assert
        mockMvc.perform(patch("/esti/cardex/{cardexId}", cardex.getIdCardex())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateForm)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idCardex").value(cardex.getIdCardex()))
                .andExpect(jsonPath("$.firstPartial").value(95))
                .andExpect(jsonPath("$.secondPartial").value(92))
                .andExpect(jsonPath("$.thirdPartial").value(94))
                .andExpect(jsonPath("$.finalGrade").value(93))
                .andExpect(jsonPath("$.updatedAt").exists());
    }

    @Test
    void shouldDeleteCardexLogicallyViaRestAPI() throws Exception {
        Cardex cardex = new Cardex();
        cardex.setStudent(testStudent);
        cardex.setTeacherSubject(testTeacherSubject);
        cardex.setPeriod(testPeriod);
        cardex.setFirstPartial(95);
        cardex.setSecondPartial(90);
        cardex.setThirdPartial(88);
        cardex.setFinalGrade(91);
        cardex = cardexRepository.save(cardex);

        Long cardexId = cardex.getIdCardex();

        mockMvc.perform(delete("/esti/cardex/{cardexId}", cardexId))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/esti/cardex/{cardexId}", cardexId))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldDeleteCardexHardViaRestAPI() throws Exception {
        // Arrange - Crear un cardex primero
        Cardex cardex = new Cardex();
        cardex.setStudent(testStudent);
        cardex.setTeacherSubject(testTeacherSubject);
        cardex.setPeriod(testPeriod);
        cardex.setFirstPartial(95);
        cardex.setSecondPartial(90);
        cardex.setThirdPartial(88);
        cardex.setFinalGrade(91);
        cardex = cardexRepository.save(cardex);

        Long cardexId = cardex.getIdCardex();

        // Act & Assert - Eliminar físicamente
        mockMvc.perform(delete("/esti/cardex/{cardexId}/hard", cardexId))
                .andExpect(status().isNoContent());

        // Verificar que fue eliminado completamente de la BD
        mockMvc.perform(get("/esti/cardex/{cardexId}", cardexId))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldGetCardexByStudent() throws Exception {
        // Arrange - Crear varios cardex para el mismo estudiante
        Cardex cardex1 = new Cardex();
        cardex1.setStudent(testStudent);
        cardex1.setTeacherSubject(testTeacherSubject);
        cardex1.setPeriod(testPeriod);
        cardex1.setFirstPartial(95);
        cardex1.setSecondPartial(90);
        cardex1.setThirdPartial(88);
        cardex1.setFinalGrade(91);
        cardexRepository.save(cardex1);

        Cardex cardex2 = new Cardex();
        cardex2.setStudent(testStudent);
        cardex2.setTeacherSubject(testTeacherSubject);
        cardex2.setPeriod(testPeriod);
        cardex2.setFirstPartial(85);
        cardex2.setSecondPartial(88);
        cardex2.setThirdPartial(90);
        cardex2.setFinalGrade(87);
        cardexRepository.save(cardex2);

        // Act & Assert
        mockMvc.perform(get("/esti/cardex/by-student/{studentId}", testStudent.getIdStudent()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].idStudent").value(testStudent.getIdStudent()))
                .andExpect(jsonPath("$[0].studentName").value("Juan"))
                .andExpect(jsonPath("$[1].idStudent").value(testStudent.getIdStudent()))
                .andExpect(jsonPath("$[1].studentName").value("Juan"));
    }

    @Test
    void shouldGetCardexByGroup() throws Exception {
        // Arrange - Crear cardex
        Cardex cardex = new Cardex();
        cardex.setStudent(testStudent);
        cardex.setTeacherSubject(testTeacherSubject);
        cardex.setPeriod(testPeriod);
        cardex.setFirstPartial(95);
        cardex.setSecondPartial(90);
        cardex.setThirdPartial(88);
        cardex.setFinalGrade(91);
        cardexRepository.save(cardex);

        // Act & Assert
        mockMvc.perform(get("/esti/cardex/by-group/{groupId}", testGroup.getIdGroup()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(1))))
                .andExpect(jsonPath("$[0].idGroup").value(testGroup.getIdGroup()))
                .andExpect(jsonPath("$[0].groupName").value("3-A"))
                .andExpect(jsonPath("$[0].grade").value(3));
    }

    @Test
    void shouldGetCardexByTeacher() throws Exception {
        // Arrange - Crear cardex
        Cardex cardex = new Cardex();
        cardex.setStudent(testStudent);
        cardex.setTeacherSubject(testTeacherSubject);
        cardex.setPeriod(testPeriod);
        cardex.setFirstPartial(95);
        cardex.setSecondPartial(90);
        cardex.setThirdPartial(88);
        cardex.setFinalGrade(91);
        cardexRepository.save(cardex);

        // Act & Assert
        mockMvc.perform(get("/esti/cardex/by-teacher/{teacherId}", testTeacher.getIdTeacher()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(1))))
                .andExpect(jsonPath("$[0].idTeacher").value(testTeacher.getIdTeacher()))
                .andExpect(jsonPath("$[0].teacherName").value("María"));
    }

    @Test
    void shouldGetCardexBySubject() throws Exception {
        // Arrange - Crear cardex
        Cardex cardex = new Cardex();
        cardex.setStudent(testStudent);
        cardex.setTeacherSubject(testTeacherSubject);
        cardex.setPeriod(testPeriod);
        cardex.setFirstPartial(95);
        cardex.setSecondPartial(90);
        cardex.setThirdPartial(88);
        cardex.setFinalGrade(91);
        cardexRepository.save(cardex);

        // Act & Assert
        mockMvc.perform(get("/esti/cardex/by-subject/{subjectId}", testSubject.getIdSubject()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(1))))
                .andExpect(jsonPath("$[0].idSubject").value(testSubject.getIdSubject()))
                .andExpect(jsonPath("$[0].subjectName").value("Matemáticas"));
    }

    @Test
    void shouldGetCardexWithPagination() throws Exception {
        // Arrange - Crear varios cardex
        for (int i = 0; i < 15; i++) {
            Cardex cardex = new Cardex();
            cardex.setStudent(testStudent);
            cardex.setTeacherSubject(testTeacherSubject);
            cardex.setPeriod(testPeriod);
            cardex.setFirstPartial(80 + i);
            cardex.setSecondPartial(85 + i);
            cardex.setThirdPartial(90 + i);
            cardex.setFinalGrade(85 + i);
            cardexRepository.save(cardex);
        }

        // Act & Assert - Primera página
        mockMvc.perform(get("/esti/cardex/")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(10)))
                .andExpect(jsonPath("$.totalElements").value(15))
                .andExpect(jsonPath("$.totalPages").value(2))
                .andExpect(jsonPath("$.number").value(0))
                .andExpect(jsonPath("$.content[0].idStudent").exists());

        // Segunda página
        mockMvc.perform(get("/esti/cardex/")
                        .param("page", "1")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(5)))
                .andExpect(jsonPath("$.number").value(1));
    }

    @Test
    void shouldSearchCardexByKeyword() throws Exception {
        // Arrange
        Cardex cardex = new Cardex();
        cardex.setStudent(testStudent);
        cardex.setTeacherSubject(testTeacherSubject);
        cardex.setPeriod(testPeriod);
        cardex.setFirstPartial(95);
        cardex.setSecondPartial(90);
        cardex.setThirdPartial(88);
        cardex.setFinalGrade(91);
        cardexRepository.save(cardex);

        // Act & Assert
        mockMvc.perform(get("/esti/cardex/search")
                        .param("keyword", "Juan")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").exists())
                .andExpect(jsonPath("$.content", hasSize(greaterThanOrEqualTo(0))));
    }

    @Test
    void shouldSearchStudentsByTeacher() throws Exception {
        // Arrange
        Cardex cardex = new Cardex();
        cardex.setStudent(testStudent);
        cardex.setTeacherSubject(testTeacherSubject);
        cardex.setPeriod(testPeriod);
        cardex.setFirstPartial(95);
        cardex.setSecondPartial(90);
        cardex.setThirdPartial(88);
        cardex.setFinalGrade(91);
        cardexRepository.save(cardex);

        // Act & Assert
        mockMvc.perform(get("/esti/cardex/searchbyTeacher")
                        .param("teacherId", testTeacher.getIdTeacher().toString())
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").exists())
                .andExpect(jsonPath("$.content", hasSize(greaterThanOrEqualTo(1))))
                .andExpect(jsonPath("$.content[0].idTeacher").value(testTeacher.getIdTeacher()));
    }

    @Test
    void shouldSearchStudentsByTeacherWithAllFilters() throws Exception {
        // Arrange
        Cardex cardex = new Cardex();
        cardex.setStudent(testStudent);
        cardex.setTeacherSubject(testTeacherSubject);
        cardex.setPeriod(testPeriod);
        cardex.setFirstPartial(95);
        cardex.setSecondPartial(90);
        cardex.setThirdPartial(88);
        cardex.setFinalGrade(91);
        cardexRepository.save(cardex);

        // Act & Assert - Con todos los filtros
        mockMvc.perform(get("/esti/cardex/searchbyTeacher")
                        .param("teacherId", testTeacher.getIdTeacher().toString())
                        .param("subjectId", testSubject.getIdSubject().toString())
                        .param("groupName", "3-A")
                        .param("grade", "3")
                        .param("keyword", "Juan")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").exists());
    }

    @Test
    void shouldReturnEmptyListWhenNoCardexFoundForStudent() throws Exception {
        // Act & Assert - Sin crear ningún cardex para este estudiante
        mockMvc.perform(get("/esti/cardex/by-student/{studentId}", 999L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    void shouldValidateGradesAreWithinRange() throws Exception {
        // Arrange - Crear cardex con calificaciones válidas
        Cardex cardex = new Cardex();
        cardex.setStudent(testStudent);
        cardex.setTeacherSubject(testTeacherSubject);
        cardex.setPeriod(testPeriod);
        cardex.setFirstPartial(100);
        cardex.setSecondPartial(0);
        cardex.setThirdPartial(50);
        cardex.setFinalGrade(50);
        cardex = cardexRepository.save(cardex);

        // Act & Assert
        mockMvc.perform(get("/esti/cardex/{cardexId}", cardex.getIdCardex()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstPartial").value(100))
                .andExpect(jsonPath("$.secondPartial").value(0))
                .andExpect(jsonPath("$.thirdPartial").value(50))
                .andExpect(jsonPath("$.finalGrade").value(50));
    }
}