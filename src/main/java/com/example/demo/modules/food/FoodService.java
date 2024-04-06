package com.example.demo.modules.food;

import com.example.demo.modules.food.dtos.CreateFoodDto;
import com.example.demo.modules.food.dtos.FoodResponseDto;
import com.example.demo.modules.kids.Kid;
import com.example.demo.modules.kids.KidRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FoodService {
    private final FoodRepository foodRepository;
    private final KidRepository kidRepository;

    public FoodResponseDto createFood(CreateFoodDto request) {

        final Optional<Food> foodInDb = foodRepository.findByName(request.getName());

        if(foodInDb.isPresent()) {
            return FoodResponseDto.builder()
                    .isError(true)
                    .message("Món ăn đã tồn tại trong hệ thống.")
                    .build();
        }

        final Food food = Food.builder()
                .name(request.getName())
                .totalAmount(request.getTotalAmount())
                .build();

        List<Kid> kids = new ArrayList<>();
        if (request.getKidIds() != null) {
            kids = kidRepository.findAllById(request.getKidIds());
            if(kids.isEmpty()) {
                return FoodResponseDto.builder()
                        .isError(true)
                        .message("Không tìm thấy trẻ nào với nhưng mã id đã cung cấp.")
                        .data(null)
                        .build();
            }
            for (Kid kid : kids) {
                List<Food> allergyFoods = kid.getAllergyFoods();
                if (allergyFoods == null) {
                    allergyFoods = new ArrayList<>();  // Initialize the list if null
                    kid.setAllergyFoods(allergyFoods);
                }
                allergyFoods.add(food);  // Add the food to the list
            }
        }
        food.setAllergyKids(kids);
        foodRepository.save(food);
        kidRepository.saveAll(kids);

        return FoodResponseDto.builder()
                .isError(false)
                .message("Món ăn đã được tạo thành công.")
                .data(food)
                .build();

    }

    public Food getFoodById(Long id) {
        return foodRepository.findById(id).orElse(null);
    }

    public FoodResponseDto getAllFoods() {

        final List<Food> foods = foodRepository.findAll();
        return FoodResponseDto.builder()
                .isError(false)
                .message("Các món ăn đã được tải thành công.")
                .data(foods)
                .build();
    }
    public FoodResponseDto updateFood(Long id, CreateFoodDto request) {
        final Food foodInDb = foodRepository.findById(id).orElse(null);
        assert foodInDb != null;

        foodInDb.setName(request.getName());
        foodInDb.setTotalAmount(request.getTotalAmount());

        List<Kid> kids = new ArrayList<>();
        if(request.getKidIds() != null) {
            kids = kidRepository.findAllById(request.getKidIds());
            for (Kid kid : kids) {
                kid.getAllergyFoods().add(foodInDb);
            }
        }
        foodInDb.setAllergyKids(kids);
        kidRepository.saveAll(kids);
        foodRepository.save(foodInDb);
        return FoodResponseDto.builder()
                .isError(false)
                .message("Món ăn đã được cập nhật thành công")
                .data(foodInDb)
                .build();

    }

    public FoodResponseDto deleteFood(Long id) {
        final Food foodInDb = foodRepository.findById(id).orElse(null);
        assert foodInDb != null;

        final List<Kid> kids = foodInDb.getAllergyKids();
        if(kids != null) {
            for (Kid kid : kids) {
                kid.getAllergyFoods().remove(foodInDb);
            }
        }
        assert kids != null;
        kidRepository.saveAll(kids);
        foodRepository.delete(foodInDb);
        return FoodResponseDto.builder()
                .isError(false)
                .message("Món ăn đã được xóa thành công")
                .build();
    }
}
