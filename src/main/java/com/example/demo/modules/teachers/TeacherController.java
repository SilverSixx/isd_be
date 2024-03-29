package com.example.demo.modules.teachers;

import com.example.demo.modules.teachers.dtos.CreateTeacherDto;
import com.example.demo.modules.teachers.dtos.TeacherResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/teacher")
@CrossOrigin("*")
public class TeacherController {
    private final TeacherService teacherService;

    @PostMapping("/create")
    public ResponseEntity<TeacherResponseDto> create(@RequestBody CreateTeacherDto request) {
        return ResponseEntity.ok(teacherService.create(request));
    }

    @GetMapping("/all")
    public ResponseEntity<?> getTeachers() {
        return ResponseEntity.ok(teacherService.getTeachers());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateTeacher(@PathVariable Long id, @RequestBody CreateTeacherDto request) {
        return ResponseEntity.ok(teacherService.update(id, request));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteTeacher(@PathVariable Long id) {
        return ResponseEntity.ok(teacherService.delete(id));
    }
}
