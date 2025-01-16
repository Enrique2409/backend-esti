package org.esti.backend_esti.Service;


import org.esti.backend_esti.DTO.LevelDTO;
import org.esti.backend_esti.Entity.Level;
import org.esti.backend_esti.Form.LevelForm;
import org.esti.backend_esti.Repository.LevelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class LevelService {

    @Autowired
    private LevelRepository levelRepository;

    public LevelDTO createLevel(final LevelForm form) {
        final Level level = new Level(form);
        level.setLevel(form.getLevel());
        levelRepository.save(level);
        level.setCreatedAt(LocalDateTime.now());
        return LevelDTO.build(level);
    }

    public LevelDTO updateLevel(final LevelForm form, Long idLevel) throws Exception {
        validateIfLevelExists(idLevel);
        final Level level = levelRepository.findById(idLevel).get();
        level.updateLevel(form);
        levelRepository.save(level);
        return LevelDTO.build(level);
    }

    public void deleteLevel(final Long idLevel) throws Exception {
        validateIfLevelExists(idLevel);
        levelRepository.deleteById(idLevel);
    }

    public LevelDTO findById(Long idLevel) throws Exception {
        validateIfLevelExists(idLevel);
        final Level level = levelRepository.findById(idLevel).get();
        return LevelDTO.build(level);
    }

    public List<LevelDTO> getAllLevels()throws Exception {
        final List<Level> levels = levelRepository.findAll();
        return levels.stream().map(LevelDTO::build).toList();
    }

    public void validateIfLevelExists(final Long idLevel) throws Exception {
        if (!levelRepository.existsById(idLevel)) {
            throw new Exception("Service not found");
        }
    }
}
