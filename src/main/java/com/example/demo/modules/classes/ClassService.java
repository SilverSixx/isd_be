package com.example.demo.modules.classes;

import com.example.demo.modules.classes.dtos.ClassResponseDto;
import com.example.demo.modules.classes.dtos.CreateClassDto;
import com.example.demo.modules.kids.Kid;
import com.example.demo.modules.kids.KidRepository;
import com.example.demo.modules.teachers.Teacher;
import com.example.demo.modules.teachers.TeacherRepository;
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

    public ClassResponseDto updateClass(Long id, CreateClassDto updateClassDto) {
        final Class classFromDb = classRepository.findById(id).orElse(null);
        if (classFromDb == null) {
            return ClassResponseDto.builder()
                    .isError(true)
                    .message("Class not found.")
                    .data(null)
                    .build();
        }
        classFromDb.setName(updateClassDto.getName());
        classFromDb.setGrade(Integer.parseInt(updateClassDto.getGrade()));
        final Teacher teacher = teacherRepository.findById(updateClassDto.getTeacherId()).orElse(null);
        assert teacher != null;
        final List<Class> classes = teacher.getClasses();
        classes.add(classFromDb);
        teacher.setClasses(classes);
        classFromDb.setTeacher(teacher);
        final List<Kid> kids = new ArrayList<>();
        for (Long i : updateClassDto.getKidIds()) {
            final Kid k = kidRepository.findById(i).orElse(null);
            if (k == null) {
                return ClassResponseDto.builder()
                        .isError(true)
                        .message("Kid with id " + i + " not found")
                        .data(null)
                        .build();
            }
            k.setClassBelongsTo(classFromDb);
            kids.add(k);
        }
        classFromDb.setKids(kids);
        classRepository.save(classFromDb);
        return ClassResponseDto.builder()
                .isError(false)
                .message("Class updated successfully.")
                .data(classFromDb)
                .build();
    }

    public ClassResponseDto deleteClass(Long id) {
        final Class classFromDb = classRepository.findById(id).orElse(null);
        if (classFromDb == null) {
            return ClassResponseDto.builder()
                    .isError(true)
                    .message("Class not found.")
                    .data(null)
                    .build();
        }

        final List<Kid> kids = classFromDb.getKids();
        if (kids != null) {
            for (Kid k : kids) {
                k.setClassBelongsTo(null);
            }
            kidRepository.saveAll(kids);
        }

        final Teacher teacher = classFromDb.getTeacher();
        if(teacher != null) {
            final List<Class> classes = teacher.getClasses();
            classes.remove(classFromDb);
            teacher.setClasses(classes);
            teacherRepository.save(teacher);
        }

        classRepository.delete(classFromDb);
        return ClassResponseDto.builder()
                .isError(false)
                .message("Class deleted successfully.")
                .data(null)
                .build();
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
