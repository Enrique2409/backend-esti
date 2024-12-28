package org.esti.backend_esti.Service;

import org.esti.backend_esti.DTO.AdminDTO;
import org.esti.backend_esti.DTO.LevelDTO;
import org.esti.backend_esti.Entity.Admin;
import org.esti.backend_esti.Entity.Level;
import org.esti.backend_esti.Form.AdminForm;
import org.esti.backend_esti.Form.LevelForm;
import org.esti.backend_esti.Repository.LevelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LevelService {

    @Autowired
    private LevelRepository levelRepository;


    public LevelDTO createLevel(final LevelForm form){
        final Level level = new Level(form);
        levelRepository.save(level);
        return LevelDTO.build(level);
    }

    public LevelDTO updateLevel(final LevelForm form, Long id) throws Exception {
        validateIfLevelExists(id);
        final Level level = levelRepository.findById(id).get();
        level.updateLevel(form);
        levelRepository.save(level);
        return LevelDTO.build(level);
    }

    public void deleteLevel(final Long id) throws Exception {
        validateIfLevelExists(id);
        levelRepository.deleteById(id);
    }

    public LevelDTO findById(Long id) throws Exception {
        validateIfLevelExists(id);
        final Level level = levelRepository.findById(id).get();
        return LevelDTO.build(level);
    }

    public List<LevelDTO> getAllLevels()throws Exception {
        final List<Level> levels = levelRepository.findAll();
        return levels.stream().map(LevelDTO::build).toList();
    }

    public void validateIfLevelExists(Long id) throws Exception {
        if (!levelRepository.existsById(id)) {
            throw new Exception("Level not found");
        }
    }
}
