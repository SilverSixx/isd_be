package com.example.demo.modules.kids;

import com.example.demo.modules.kids.dtos.CreateKidDto;
import com.example.demo.modules.kids.dtos.KidResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/kid")
@CrossOrigin("*")
public class KidController {
    private final KidService kidService;

    @PostMapping("/create")
    public ResponseEntity<KidResponseDto> create(@RequestBody CreateKidDto request) {
        return ResponseEntity.ok(kidService.create(request));
    }

    @GetMapping("/all")
    public ResponseEntity<KidResponseDto> getKids() {
        return ResponseEntity.ok(kidService.findAll());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<KidResponseDto> update(@PathVariable Long id, @RequestBody CreateKidDto request) {
        return ResponseEntity.ok(kidService.update(id, request));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<KidResponseDto> delete(@PathVariable Long id) {
        return ResponseEntity.ok(kidService.delete(id));
    }
}
