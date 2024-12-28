package org.esti.backend_esti.Controller;

import jakarta.validation.Valid;
import org.esti.backend_esti.DTO.LevelDTO;
import org.esti.backend_esti.Form.LevelForm;
import org.esti.backend_esti.Service.LevelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/esti/level")
public class LevelController {

    @Autowired
    private LevelService levelService;

    @PostMapping("/")
    public ResponseEntity createLevel(@RequestBody @Valid LevelForm form) {
        LevelDTO levelDTO = levelService.createLevel(form);
        return ResponseEntity.ok().body(levelDTO);
    }

    @PatchMapping("/{levelId}")
    public ResponseEntity updateLevel (@RequestBody @Valid LevelForm form, @PathVariable("levelId") final Long levelId) throws Exception {
        LevelDTO levelDTO = levelService.updateLevel(form, levelId);
        return ResponseEntity.ok().body(levelId);
    }

    @DeleteMapping("/{levelId}")
    public ResponseEntity deleteLevel(@PathVariable("levelId") final Long levelId) throws Exception {
        levelService.deleteLevel(levelId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{levelId}")
    public ResponseEntity findById(@PathVariable("levelId") final Long levelId) throws Exception {
        LevelDTO levelDTO = levelService.findById(levelId);
        return ResponseEntity.ok().body(levelId);
    }

    @GetMapping("/getAll")
    public ResponseEntity getAllLevels() throws Exception {
        List<LevelDTO> levelsDTO = levelService.getAllLevels();
        return ResponseEntity.ok().body(levelsDTO);
    }

}
