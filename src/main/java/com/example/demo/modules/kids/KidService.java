package com.example.demo.modules.kids;

import com.example.demo.modules.classes.Class;
import com.example.demo.modules.classes.ClassRepository;
import com.example.demo.modules.food.Food;
import com.example.demo.modules.food.FoodRepository;
import com.example.demo.modules.kids.dtos.CreateKidDto;
import com.example.demo.modules.kids.dtos.KidResponseDto;
import com.example.demo.modules.parents.Parent;
import com.example.demo.modules.parents.ParentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class KidService {
    private final KidRepository kidRepository;
    private final ClassRepository classRepository;
    private final ParentRepository parentRepository;
    private final FoodRepository foodRepository;

    public KidResponseDto create(CreateKidDto kid) {
        // Extracting year, month, and day from the date of birth
        final String[] dobParts = kid.getDob().split("-");
        final int year = Integer.parseInt(dobParts[0]);
        final int month = Integer.parseInt(dobParts[1]);
        final int day = Integer.parseInt(dobParts[2]);

        // Creating a Date object from year, month, and day
        final Date dob = new Date(year - 1900, month - 1, day + 1);

        // Creating a new Kid object
        final Kid newKid = Kid.builder()
                .fullName(kid.getName())
                .nickName(kid.getNickName())
                .dob(dob)
                .allergyFoods(null)
                .parent(null)
                .classBelongsTo(null)
                .build();

        if (kid.getParentId() != null) {
            Parent parentFromDb = parentRepository.findById(kid.getParentId()).orElse(null);
            assert parentFromDb != null;
            parentFromDb.setKid(newKid);
            newKid.setParent(parentFromDb);
            parentRepository.save(parentFromDb);
        }

        if (kid.getClassId() != null) {
            Class classFromDb = classRepository.findById(kid.getClassId()).orElse(null);
            assert classFromDb != null;
            classFromDb.getKids().add(newKid);
            newKid.setClassBelongsTo(classFromDb);
            classRepository.save(classFromDb);
        }

        if (kid.getFoodIds() != null && !kid.getFoodIds().isEmpty()) {
            List<Food> allergyFoods = new ArrayList<>();
            for (Long foodId : kid.getFoodIds()) {
                final Food foodFromDb = foodRepository.findById(foodId).orElse(null);
                if (foodFromDb != null) {
                    List<Kid> allergyKids = foodFromDb.getAllergyKids();
                    if (allergyKids == null) {
                        allergyKids = new ArrayList<>(); // Initialize the list if null
                        foodFromDb.setAllergyKids(allergyKids);
                    }
                    allergyKids.add(newKid);  // Add the new kid to the food's list of allergy kids

                    allergyFoods.add(foodFromDb);  // Add the food to the new kid's list of allergy foods
                }
            }
            newKid.setAllergyFoods(allergyFoods);  // Set the allergy foods for the new kid
            foodRepository.saveAll(allergyFoods);  // Save the changes to the database
        }

        kidRepository.save(newKid);

        return KidResponseDto.builder()
                .isError(false)
                .message("Kid created successfully.")
                .data(newKid)
                .build();
    }

    public KidResponseDto findAll() {
        final List<Kid> kids = kidRepository.findAll();
        return KidResponseDto.builder()
                .isError(false)
                .message("Kids fetched successfully.")
                .data(kids)
                .build();
    }

    public KidResponseDto update(Long id, CreateKidDto updateKidDto) {
        final Kid kidFromDb = kidRepository.findById(id).orElse(null);
        if (kidFromDb == null) {
            return KidResponseDto.builder()
                    .isError(true)
                    .message("Kid not found.")
                    .build();
        }
        kidFromDb.setFullName(updateKidDto.getName());
        kidFromDb.setNickName(updateKidDto.getNickName());

        final Parent parent = parentRepository.findById(updateKidDto.getParentId()).orElse(null);
        assert parent != null;
        parent.setKid(kidFromDb);
        kidFromDb.setParent(parent);

        final Class classBelongsTo = classRepository.findById(updateKidDto.getClassId()).orElse(null);
        assert classBelongsTo != null;
        classBelongsTo.getKids().add(kidFromDb);
        kidFromDb.setClassBelongsTo(classBelongsTo);

        final List<Food> foods = foodRepository.findAllById(updateKidDto.getFoodIds());
        for (Food food : foods) {
            food.getAllergyKids().add(kidFromDb);
            if(!kidFromDb.getAllergyFoods().contains(food)){
                kidFromDb.getAllergyFoods().add(food);
            }
        }

        kidRepository.save(kidFromDb);
        parentRepository.save(parent);
        classRepository.save(classBelongsTo);
        foodRepository.saveAll(foods);

        return KidResponseDto.builder()
                .isError(false)
                .message("Kid updated successfully.")
                .data(kidFromDb)
                .build();
    }

    public KidResponseDto delete(Long id) {
        final Kid kidFromDb = kidRepository.findById(id).orElse(null);
        if (kidFromDb == null) {
            return KidResponseDto.builder()
                    .isError(true)
                    .message("Kid not found.")
                    .build();
        }

        final Parent parent = kidFromDb.getParent();
        if (parent != null) {
            parent.setKid(null);
            parentRepository.save(parent);
        }

        final Class classBelongsTo = kidFromDb.getClassBelongsTo();
        if (classBelongsTo != null) {
            classBelongsTo.getKids().remove(kidFromDb);
            classRepository.save(classBelongsTo);
        }

        final List<Food> foods = kidFromDb.getAllergyFoods();
        if(foods != null){
            for (Food food : foods) {
                food.getAllergyKids().remove(kidFromDb);
            }
            foodRepository.saveAll(foods);
        }
        kidRepository.delete(kidFromDb);
        return KidResponseDto.builder()
                .isError(false)
                .message("Kid deleted successfully.")
                .build();
    }
}
