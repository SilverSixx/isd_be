package com.example.demo.modules.classes.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateClassDto {
    private String name;
    private String grade;
    private Long teacherId;
    private List<Long> kidIds;
}
