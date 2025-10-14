package org.esti.backend_esti.Service;

import org.esti.backend_esti.DTO.PeriodDTO;
import org.esti.backend_esti.Entity.Period;
import org.esti.backend_esti.Form.PeriodForm;
import org.esti.backend_esti.Repository.PeriodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PeriodService {

    @Autowired
    private PeriodRepository periodRepository;

    public PeriodDTO createPeriod(final PeriodForm form) {
        final Period period = new Period(form);
        period.setCreatedAt(LocalDateTime.now());
        periodRepository.save(period);
        return PeriodDTO.build(period);
    }

    public PeriodDTO updatePeriod(final PeriodForm form, Long idPeriod) throws Exception {
        validateIfPeriodExists(idPeriod);
        final Period period = periodRepository.findById(idPeriod).get();
        period.updatePeriod(form);
        periodRepository.save(period);
        return PeriodDTO.build(period);
    }

    public void deletePeriod(final Long idPeriod) throws Exception {
        validateIfPeriodExists(idPeriod);
        periodRepository.deleteById(idPeriod);
    }

    public PeriodDTO findById(Long idPeriod) throws Exception {
        validateIfPeriodExists(idPeriod);
        final Period period = periodRepository.findById(idPeriod).orElseThrow(() ->
                new Exception("Period not found with id: " + idPeriod)
        );
        return PeriodDTO.build(period);
    }

    public List<PeriodDTO> getAllPeriods() {
        final List<Period> periods = periodRepository.findAll();
        return periods.stream().map(PeriodDTO::build).toList();
    }

    public List<Period> getAllActivePeriods() {
        return periodRepository.findAllActive();
    }

    public void deletePeriodLogically(Long idPeriod) throws Exception {
        Period existingPeriod = periodRepository.findById(idPeriod).orElseThrow(() ->
                new Exception("Period not found with id: " + idPeriod)
        );
        existingPeriod.markAsDeleted();
        periodRepository.save(existingPeriod);
    }

    public void validateIfPeriodExists(Long idPeriod) throws Exception {
        if (!periodRepository.existsById(idPeriod)) {
            throw new Exception("Period not found");
        }
    }
}
