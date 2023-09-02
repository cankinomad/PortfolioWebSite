package org.berka.controller;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.berka.Repository.entity.Work;
import org.berka.constant.EndPoints;
import org.berka.dto.request.SaveRequestDto;
import org.berka.dto.request.UpdateRequestDto;
import org.berka.service.WorkService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.List;

import static org.berka.constant.EndPoints.*;

@RestController
@RequestMapping(WORK)
@RequiredArgsConstructor
public class WorkController {

    private final WorkService service;


    @GetMapping
    public ResponseEntity<List<Work>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Work> findWorkById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findWorkById(id));
    }

    @PostMapping(SAVE)
    public ResponseEntity<String> saveWork(@Valid @RequestBody SaveRequestDto dto) {
        return ResponseEntity.ok(service.saveWork(dto));
    }

    @PutMapping(UPDATE)
    public ResponseEntity<String> updateWork(@Valid @RequestBody UpdateRequestDto dto){
        return ResponseEntity.ok(service.updateWork(dto));
    }

    @DeleteMapping(DELETE)
    public ResponseEntity<Boolean> deleteWork(@RequestParam String token) {
        return ResponseEntity.ok(service.deleteWork(token));
    }
}
