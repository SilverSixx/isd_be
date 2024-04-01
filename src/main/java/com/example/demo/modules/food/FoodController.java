package com.example.demo.modules.food;

import com.example.demo.modules.food.dtos.CreateFoodDto;
import com.example.demo.modules.food.dtos.FoodResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/food")
@CrossOrigin("*")
public class FoodController {
    private final FoodService foodService;

    @GetMapping("/all")
    public ResponseEntity<FoodResponseDto> getFoods() {
        return ResponseEntity.ok(foodService.getAllFoods());
    }

    @PostMapping("/create")
    public ResponseEntity<FoodResponseDto> create(@RequestBody CreateFoodDto request) {
        return ResponseEntity.ok(foodService.createFood(request));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<FoodResponseDto> update(@PathVariable Long id, @RequestBody CreateFoodDto request) {
        return ResponseEntity.ok(foodService.updateFood(id, request));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<FoodResponseDto> delete(@PathVariable Long id) {
        return ResponseEntity.ok(foodService.deleteFood(id));
    }
}
