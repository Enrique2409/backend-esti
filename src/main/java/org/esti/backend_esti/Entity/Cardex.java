package org.esti.backend_esti.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.esti.backend_esti.Form.CardexForm;
import org.hibernate.annotations.Where;
import java.time.LocalDateTime;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Where(clause = "deleted_at IS NULL")
@Table(name = "cardex")
public class Cardex {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cardex")
    private Long idCardex;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "id_student", nullable = false)
    private Student student;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "id_teacher_subject", nullable = false)
    private TeacherSubject teacherSubject;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_period")
    private Period period;

    @Column(name="first_partial")
    private Integer firstPartial;

    @Column(name="second_partial")
    private Integer secondPartial;

    @Column(name="third_partial")
    private Integer thirdPartial;

    @Column(name="final_grade")
    private Integer finalGrade;

    @NotNull
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    public Cardex (Long idCardex) { this.idCardex = idCardex; }

    public Cardex(final CardexForm form, final Student student, final TeacherSubject teacherSubject, final Period period) {
        this.student = student;
        this.teacherSubject = teacherSubject;
        this.period = period;
        this.firstPartial = form.getFirstPartial();
        this.secondPartial = form.getSecondPartial();
        this.thirdPartial = form.getThirdPartial();
        this.finalGrade = form.getFinalGrade();
    }

    public void updateFromForm(final CardexForm form, final Student student, final TeacherSubject teacherSubject, final Period period) {
        this.student = student;
        this.teacherSubject = teacherSubject;
        this.period = period;
        this.firstPartial = form.getFirstPartial();
        this.secondPartial = form.getSecondPartial();
        this.thirdPartial = form.getThirdPartial();
        this.finalGrade = form.getFinalGrade();
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
