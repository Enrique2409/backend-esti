package org.esti.backend_esti.DTO;

import lombok.Builder;
import lombok.Data;
import org.esti.backend_esti.Entity.Period;

@Data
@Builder
public class PeriodDTO {
    private Long idPeriod;

    private String cve;

    private String description;

    public static PeriodDTO build (final Period period){
        return PeriodDTO.builder()
                .idPeriod(period.getIdPeriod())
                .cve(period.getCve())
                .description(period.getDescription())
                .build();
    }
}
