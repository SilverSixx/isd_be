package com.example.demo.modules.food.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateFoodDto {
    private String name;
    private Double totalAmount;
    private List<Long> kidIds;
}
