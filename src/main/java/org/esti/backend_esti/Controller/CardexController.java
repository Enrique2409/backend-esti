package org.esti.backend_esti.Controller;

import jakarta.validation.Valid;
import org.esti.backend_esti.DTO.CardexDTO;
import org.esti.backend_esti.Form.CardexForm;
import org.esti.backend_esti.Service.CardexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/esti/cardex")
public class CardexController {

    private final CardexService cardexService;

    @Autowired
    public CardexController(CardexService cardexService) {
        this.cardexService = cardexService;
    }

    @PostMapping("/create-cardex")
    public ResponseEntity<CardexDTO> createCardex(@RequestBody @Valid CardexForm form) throws Exception {
        CardexDTO cardexDTO = cardexService.createCardex(form);
        return ResponseEntity.ok(cardexDTO);
    }

    @PatchMapping("/{cardexId}")
    public ResponseEntity<CardexDTO> updateCardex(@RequestBody @Valid CardexForm form, @PathVariable("cardexId") Long cardexId) throws Exception {
        CardexDTO cardexDTO = cardexService.updateCardex(form, cardexId);
        return ResponseEntity.ok(cardexDTO);
    }

    @DeleteMapping("/{cardexId}/hard")
    public ResponseEntity<Void> deleteCardex(@PathVariable("cardexId") Long cardexId) throws Exception {
        cardexService.deleteCardex(cardexId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{cardexId}")
    public ResponseEntity<Void> deleteCardexLogically(@PathVariable("cardexId") Long cardexId) throws Exception {
        cardexService.deleteCardexLogically(cardexId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{cardexId}")
    public ResponseEntity<CardexDTO> findById(@PathVariable("cardexId") Long cardexId) throws Exception {
        CardexDTO cardexDTO = cardexService.findById(cardexId);
        return ResponseEntity.ok(cardexDTO);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<CardexDTO>> getAllCardex() {
        List<CardexDTO> cardexList = cardexService.getAllCardex();
        return ResponseEntity.ok(cardexList);
    }

    @GetMapping("/by-group/{groupId}")
    public ResponseEntity<List<CardexDTO>> getByGroup(@PathVariable("groupId") Long groupId) {
        List<CardexDTO> filtered = cardexService.getByGroup(groupId);
        return ResponseEntity.ok(filtered);
    }

    @GetMapping("/by-student/{studentId}")
    public ResponseEntity<List<CardexDTO>> getByStudent(@PathVariable("studentId") Long studentId) {
        List<CardexDTO> filtered = cardexService.getByStudent(studentId);
        return ResponseEntity.ok(filtered);
    }

    @GetMapping("/by-teacher/{teacherId}")
    public ResponseEntity<List<CardexDTO>> getByTeacher(@PathVariable("teacherId") Long teacherId) {
        List<CardexDTO> filtered = cardexService.getByTeacher(teacherId);
        return ResponseEntity.ok(filtered);
    }

    @GetMapping("/by-subject/{subjectId}")
    public ResponseEntity<List<CardexDTO>> getBySubject(@PathVariable("subjectId") Long subjectId) {
        List<CardexDTO> filtered = cardexService.getBySubject(subjectId);
        return ResponseEntity.ok(filtered);
    }
}
