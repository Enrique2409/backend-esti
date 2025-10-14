package org.esti.backend_esti.Entity;

import lombok.*;

@Getter
@AllArgsConstructor
public enum ReportStatus {
    EN_PROGRESO("En progreso"),
    FINALIZADO("Finalizado"),
    CANCELADO("Cancelado");

    private final String status;
}
