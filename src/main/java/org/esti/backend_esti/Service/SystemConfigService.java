package org.esti.backend_esti.Service;


import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.esti.backend_esti.DTO.LockedResponse;
import org.esti.backend_esti.DTO.SystemConfigDTO;
import org.esti.backend_esti.Entity.SystemConfig;
import org.esti.backend_esti.Form.LockedGradesForm;
import org.esti.backend_esti.Repository.SystemConfigRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class SystemConfigService {

    private final SystemConfigRepository systemConfigRepository;

    public SystemConfigDTO getConfiguration() {
        SystemConfig config = systemConfigRepository.findById(1L)
                .orElseGet(() -> {
                    SystemConfig newConfig = new SystemConfig();
                    newConfig.setId(1L);
                    newConfig.setLockedGrades(false);
                    return systemConfigRepository.save(newConfig);
                });
        return converterToDTO(config);
    }

    public Boolean areLockedGrades() {
        return getConfiguration().getLockedGrades();
    }

    @Transactional
    public LockedResponse changeStatusGrades(LockedGradesForm form, Long adminId) {
        SystemConfig config = systemConfigRepository.findById(1L)
                .orElseGet(() -> {
                    SystemConfig newConfig = new SystemConfig();
                    newConfig.setId(1L);
                    return newConfig;
                });
        config.setLockedGrades(form.getLock());
        config.setLockedDate(LocalDateTime.now());
        config.setLockedBy(adminId);
        config.setNotes(form.getNotes());

        systemConfigRepository.save(config);

        String message = form.getLock()
                ? "Calificaciones bloqueadas exitosamente, los profesores ya no pueden modificarlas."
                : "Calificaciones desbloqueadas, los profesores pueden modificarlas nuevamente.";

        return new LockedResponse(true, message, config.getLockedGrades());
    }

    private SystemConfigDTO converterToDTO(SystemConfig entity) {
        return new SystemConfigDTO(
                entity.getId(),
                entity.getLockedGrades(),
                entity.getLockedDate(),
                entity.getLockedBy(),
                entity.getNotes()
        );
    }
}
