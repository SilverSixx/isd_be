package com.example.demo.modules.classes;

import com.example.demo.modules.classes.dtos.ClassResponseDto;
import com.example.demo.modules.classes.dtos.CreateClassDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/class")
@CrossOrigin("*")
public class ClassController {
    private final ClassService classService;

    @PostMapping("/create")
    public ResponseEntity<ClassResponseDto> createClass(@RequestBody CreateClassDto request) {
        return ResponseEntity.ok().body(classService.createClass(request));
    }

    @GetMapping("/all")
    public ResponseEntity<?> getClasses() {
        return ResponseEntity.ok(classService.getClasses());
    }

}
