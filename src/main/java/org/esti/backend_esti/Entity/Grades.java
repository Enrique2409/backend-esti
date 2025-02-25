package org.esti.backend_esti.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "grades", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"student_id", "subject_teacher_id"})
})
public class Grades {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_grade")
    private Long idGrade;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", referencedColumnName = "id_student", nullable = false)
    private Student student;

    @Min(0)
    @Max(10)
    @NotNull
    @Column(name = "grade", nullable = false)
    private Double grade;

    @NotNull
    @Column(name = "evaluation_date", nullable = false)
    private LocalDateTime evaluationDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subject_teacher_id", referencedColumnName = "id_subject_teacher", nullable = false)
    private SubjectTeacher subjectTeacher;

    @NotNull
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    public Grades(final GradesForm form) {
        this.student = form.getStudent();
        this.grade = form.getGrade();
        this.evaluationDate = form.getEvaluationDate();
        this.subjectTeacher = form.getSubjectTeacher();
    }

    public void updateGrades(final GradesForm form) {
        if (form.getStudent() != null) {
            this.student = form.getStudent();
        }
        if (form.getGrade() != null) {
            this.grade = form.getGrade();
        }
        if (form.getSubjectTeacher() != null) {
            this.subjectTeacher = form.getSubjectTeacher();
        }
    }

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();   
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public void markAsDeleted() {
        this.deletedAt = LocalDateTime.now();
    }
}
