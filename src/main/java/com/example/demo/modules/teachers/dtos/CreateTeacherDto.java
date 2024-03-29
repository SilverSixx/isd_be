package com.example.demo.modules.teachers.dtos;

import lombok.*;

import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateTeacherDto {
    private String fullName;
    private String username;
    private String password;
    private List<Long> classIds;
}
