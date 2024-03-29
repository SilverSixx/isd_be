package com.example.demo.modules.classes;

import com.example.demo.modules.classes.dtos.ClassResponseDto;
import com.example.demo.modules.classes.dtos.CreateClassDto;
import com.example.demo.modules.kids.Kid;
import com.example.demo.modules.kids.KidRepository;
import com.example.demo.modules.kids.dtos.KidResponseDto;
import com.example.demo.modules.teachers.TeacherRepository;
import com.example.demo.modules.teachers.dtos.TeacherResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ClassService {
    private final ClassRepository classRepository;
    private final TeacherRepository teacherRepository;
    private final KidRepository kidRepository;

    public ClassResponseDto createClass(CreateClassDto request) {
        final Class classFromDb = classRepository.findByName(request.getName()).orElseGet(() -> null);
        if (classFromDb != null) {
            return ClassResponseDto.builder()
                    .isError(true)
                    .message("Class already exists.")
                    .data(null)
                    .build();
        }

        final int grade = Integer.parseInt(request.getGrade());
        final Class newClass = Class.builder()
                .name(request.getName())
                .grade(grade)
                .teacher(request.getTeacherId() != null ? teacherRepository.findById(request.getTeacherId()).orElse(null) : null)
                .kids(null)
                .build();

        List<Kid> kids = new ArrayList<>();
        for (Long id : request.getKidIds()) {
            final Kid k = kidRepository.findById(id).orElse(null);
            if (k == null) {
                return ClassResponseDto.builder()
                        .isError(true)
                        .message("Kid with id " + id + " not found")
                        .data(null)
                        .build();
            }
            k.setClassBelongsTo(newClass);
            kids.add(k);
        }
        newClass.setKids(kids);
        classRepository.save(newClass);
        return ClassResponseDto.builder()
                .isError(false)
                .message("Class created successfully.")
                .data(newClass)
                .build();

    }

    public void updateClass() {
    }

    public void deleteClass() {
    }

    public ClassResponseDto getClasses() {
        final List<Class> classes = classRepository.findAll();
        return ClassResponseDto.builder()
                .isError(false)
                .message("Classes fetched successfully")
                .data(classes)
                .build();
    }

}
