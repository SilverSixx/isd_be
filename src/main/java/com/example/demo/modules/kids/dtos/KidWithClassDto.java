package com.example.demo.modules.kids.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class KidWithClassDto {
    private Long kidId;
    private Long classId;
}
