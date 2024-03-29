package com.example.demo.modules.parents;

import com.example.demo.modules.parents.dtos.CreateParentDto;
import com.example.demo.modules.parents.dtos.ParentResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/parent")
@CrossOrigin("*")
public class ParentController {
    private final ParentService parentService;

    @PostMapping("/create")
    public ResponseEntity<ParentResponseDto> createParent(@RequestBody CreateParentDto request) {
        return ResponseEntity.ok(parentService.create(request));
    }

    @GetMapping("/all")
    public ResponseEntity<ParentResponseDto> getParents() {
        return ResponseEntity.ok(parentService.getParents());
    }
}
