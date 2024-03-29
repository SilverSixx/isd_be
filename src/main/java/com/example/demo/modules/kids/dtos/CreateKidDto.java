package com.example.demo.modules.kids.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateKidDto {
    private String name;
    private String nickname;
    private String dob;
    private Long parentId;
    private Long classId;
}
