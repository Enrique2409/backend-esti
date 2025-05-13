package org.esti.backend_esti.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.esti.backend_esti.Entity.Admin;
import org.esti.backend_esti.Entity.CourseClass;
import org.esti.backend_esti.Entity.Level;
import org.esti.backend_esti.Entity.Student;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StudentDTO {

    private Long idStudent;
    private String name;
    private String lastNamePaternal;
    private String lastNameMaternal;
    private String curp;
    private LocalDate birthDate;
    private String phoneNumber;
    //private CourseClass courseClass;

    public static StudentDTO build(final Student student) {
        return StudentDTO.builder()
                .idStudent(student.getIdStudent())
                .name(student.getName())
                .lastNamePaternal(student.getLastNamePaternal())
                .lastNameMaternal(student.getLastNameMaternal())
                .curp(student.getCurp())
                .birthDate(student.getBirthDate())
                .phoneNumber(student.getPhoneNumber())
                //.courseClass(student.getCourseClass())
                .build();
    }

} 