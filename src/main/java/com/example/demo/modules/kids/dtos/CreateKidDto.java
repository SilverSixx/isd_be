package com.example.demo.modules.kids.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateKidDto {
    private String name;
    private String nickName;
    private String dob;
    private Long parentId;
    private Long classId;
}
