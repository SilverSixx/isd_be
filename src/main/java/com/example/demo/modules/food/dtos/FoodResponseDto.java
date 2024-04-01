package com.example.demo.modules.food.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FoodResponseDto {
    private boolean isError;
    private String message;
    private Object data;
}
