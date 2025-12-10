package org.esti.backend_esti.Entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "system_config")
@AllArgsConstructor
@NoArgsConstructor
public class SystemConfig {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "locked_grades", nullable = false)
    private Boolean lockedGrades = false;

    @Column(name = "locked_date")
    private LocalDateTime lockedDate;

    @Column(name = "locked_by")
    private Long lockedBy;

    @Column(name = "notes", columnDefinition = "TEXT")
    private String notes;
}
