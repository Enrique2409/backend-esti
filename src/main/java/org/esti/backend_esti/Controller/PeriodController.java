package org.esti.backend_esti.Controller;

import jakarta.validation.Valid;
import org.esti.backend_esti.DTO.PeriodDTO;
import org.esti.backend_esti.Entity.Period;
import org.esti.backend_esti.Form.PeriodForm;
import org.esti.backend_esti.Service.PeriodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/esti/period")
public class PeriodController {

    @Autowired
    private PeriodService periodService;

    @PostMapping("/create-period")
    public ResponseEntity<PeriodDTO> createPeriod(@RequestBody @Valid PeriodForm form) {
        PeriodDTO periodDTO = periodService.createPeriod(form);
        return ResponseEntity.ok().body(periodDTO);
    }

    @PatchMapping("/{periodId}")
    public ResponseEntity<PeriodDTO> updatePeriod(@RequestBody @Valid PeriodForm form, @PathVariable("periodId") final Long periodId) throws Exception {
        PeriodDTO periodDTO = periodService.updatePeriod(form, periodId);
        return ResponseEntity.ok().body(periodDTO);
    }

    @DeleteMapping("/{periodId}/hard")
    public ResponseEntity<Void> deletePeriod(@PathVariable("periodId") final Long periodId) throws Exception {
        periodService.deletePeriod(periodId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{periodId}")
    public ResponseEntity<Void> deletePeriodLogically(@PathVariable Long periodId) throws Exception {
        periodService.deletePeriodLogically(periodId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{periodId}")
    public ResponseEntity<PeriodDTO> findById(@PathVariable("periodId") final Long periodId) throws Exception {
        PeriodDTO periodDTO = periodService.findById(periodId);
        return ResponseEntity.ok().body(periodDTO);
    }

    @GetMapping("/all")
    public ResponseEntity<List<PeriodDTO>> getAllPeriods() {
        List<PeriodDTO> periodsDTO = periodService.getAllPeriods();
        return ResponseEntity.ok().body(periodsDTO);
    }

    @GetMapping("/active")
    public ResponseEntity<List<PeriodDTO>> getAllActivePeriods() {
        List<Period> activePeriods = periodService.getAllActivePeriods();
        List<PeriodDTO> activePeriodDTOs = activePeriods.stream().map(PeriodDTO::build).toList();
        return ResponseEntity.ok(activePeriodDTOs);
    }
}
