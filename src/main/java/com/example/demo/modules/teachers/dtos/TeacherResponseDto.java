package com.example.demo.modules.teachers.dtos;

import lombok.*;
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeacherResponseDto {
    private boolean isError;
    private String message;
    private Object data;
}
