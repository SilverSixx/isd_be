package com.example.demo.modules.classes.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ClassResponseDto {
    private boolean isError;
    private String message;
    private Object data;
}
