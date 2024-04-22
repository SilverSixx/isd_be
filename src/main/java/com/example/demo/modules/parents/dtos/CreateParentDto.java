package com.example.demo.modules.parents.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateParentDto {
    private String fullName;
    private String username;
    private String password;
    private Long kidId;
    private String idCardNumber;
}
