package org.esti.backend_esti.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.esti.backend_esti.Form.CardexForm;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "cardex")
public class Cardex {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cardex")
    private Long idCardex;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_group", nullable = false)
    private Group group;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_teacher", nullable = false)
    private Teacher teacher;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_student", nullable = false)
    private Student student;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_subject", nullable = false)
    private Subject subject;

    @Column(name="first_partial")
    private Integer firstPartial;

    @Column(name="second_partial")
    private Integer secondPartial;

    @Column(name="third_partial")
    private Integer thirdPartial;

    @Column(name="final_grade")
    private Integer final_grade;

    @NotNull
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    public Cardex (Long idCardex) { this.idCardex = idCardex; }

    public Cardex(final CardexForm form, final Group group, final Teacher teacher, final Student student, final Subject subject) {
        this.group = group;
        this.teacher = teacher;
        this.student = student;
        this.subject = subject;
        this.firstPartial = form.getFirstPartial();
        this.secondPartial = form.getSecondPartial();
        this.thirdPartial = form.getThirdPartial();
        this.final_grade = form.getFinalGrade();
    }

    public void updateFromForm(final CardexForm form, final Group group, final Teacher teacher, final Student student, final Subject subject) {
        this.group = group;
        this.teacher = teacher;
        this.student = student;
        this.subject = subject;
        this.firstPartial = form.getFirstPartial();
        this.secondPartial = form.getSecondPartial();
        this.thirdPartial = form.getThirdPartial();
        this.final_grade = form.getFinalGrade();
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
