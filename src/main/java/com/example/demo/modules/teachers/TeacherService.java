package com.example.demo.modules.teachers;

import com.example.demo.modules.auth.Role;
import com.example.demo.modules.classes.Class;
import com.example.demo.modules.classes.ClassRepository;
import com.example.demo.modules.teachers.dtos.CreateTeacherDto;
import com.example.demo.modules.teachers.dtos.TeacherResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TeacherService {
    private final TeacherRepository teacherRepository;
    private final ClassRepository classRepository;
    private final PasswordEncoder passwordEncoder;

    public TeacherResponseDto getTeachers() {
        final List<Teacher> teachers = teacherRepository.findAll();
        return TeacherResponseDto.builder()
                .isError(false)
                .message("Teachers fetched successfully")
                .data(teachers)
                .build();
    }

    public void getTeacherById(Long id) {
        teacherRepository.findById(id);
    }

    public void deleteTeacherById(Long id) {
        teacherRepository.deleteById(id);
    }

    public TeacherResponseDto create(CreateTeacherDto createTeacherDto) {
        final Teacher teacherInDb = teacherRepository.findByUsername(createTeacherDto.getUsername()).orElse(null);
        if (teacherInDb != null) {
            return TeacherResponseDto.builder()
                    .isError(true)
                    .message("Teacher with username " + createTeacherDto.getUsername() + " already exists")
                    .data(null)
                    .build();
        }
        List<Class> classes = new ArrayList<>();
        if (createTeacherDto.getClassIds() != null) {
            for (Long classId : createTeacherDto.getClassIds()) {
                final Class classBelongsTo = classRepository.findById(classId).orElse(null);
                if (classBelongsTo == null) {
                    return TeacherResponseDto.builder()
                            .isError(true)
                            .message("Class with id " + classId + " not found")
                            .data(null)
                            .build();
                }
                classes.add(classBelongsTo);
            }
        }

        final Teacher teacher = Teacher.builder()
                .fullName(createTeacherDto.getFullName())
                .username(createTeacherDto.getUsername())
                .password(passwordEncoder.encode(createTeacherDto.getPassword()))
                .role(Role.TEACHER)
                .classes(classes)
                .build();

        teacherRepository.save(teacher);

        return TeacherResponseDto.builder()
                .isError(false)
                .message("Teacher created successfully")
                .data(teacher)
                .build();

    }

    public TeacherResponseDto update(Long id, CreateTeacherDto updateTeacherDto) {
        final Teacher teacherInDb = teacherRepository.findById(id).orElse(null);
        if (teacherInDb == null) {
            return TeacherResponseDto.builder()
                    .isError(true)
                    .message("Teacher with id " + id + " not found")
                    .data(null)
                    .build();
        }
        teacherInDb.setFullName(updateTeacherDto.getFullName());
        teacherInDb.setUsername(updateTeacherDto.getUsername());
        teacherInDb.setPassword(passwordEncoder.encode(updateTeacherDto.getPassword()));
        final List<Class> classes = new ArrayList<>();
        for (Long classId : updateTeacherDto.getClassIds()) {
            final Class classBelongsTo = classRepository.findById(classId).orElse(null);
            if (classBelongsTo == null) {
                return TeacherResponseDto.builder()
                        .isError(true)
                        .message("Class with id " + classId + " not found")
                        .data(null)
                        .build();
            }
            classBelongsTo.setTeacher(teacherInDb);
            classes.add(classBelongsTo);
        }
        teacherInDb.setClasses(classes);
        teacherRepository.save(teacherInDb);
        return TeacherResponseDto.builder()
                .isError(false)
                .message("Teacher updated successfully")
                .data(teacherInDb)
                .build();
    }

    public TeacherResponseDto delete(Long id) {
        final Teacher teacherFromDb = teacherRepository.findById(id).orElse(null);
        if (teacherFromDb == null) {
            return TeacherResponseDto.builder()
                    .isError(true)
                    .message("Teacher not found.")
                    .build();
        }

        final List<Class> classes = teacherFromDb.getClasses();
        if(classes != null) {
            for (Class c : classes) {
                c.setTeacher(null);
            }
            classRepository.saveAll(classes);
        }

        teacherFromDb.setClasses(null);

        teacherRepository.deleteById(id);
        return TeacherResponseDto.builder()
                .isError(false)
                .message("Teacher deleted successfully.")
                .build();
    }

}
