package com.example.demo.modules.kids;

import com.example.demo.modules.classes.Class;
import com.example.demo.modules.classes.ClassRepository;
import com.example.demo.modules.kids.dtos.CreateKidDto;
import com.example.demo.modules.kids.dtos.KidResponseDto;
import com.example.demo.modules.parents.Parent;
import com.example.demo.modules.parents.ParentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class KidService {
    private final KidRepository kidRepository;
    private final ClassRepository classRepository;
    private final ParentRepository parentRepository;

    public KidResponseDto create(CreateKidDto kid) {
        // Extracting year, month, and day from the date of birth
        final String[] dobParts = kid.getDob().split("-");
        final int year = Integer.parseInt(dobParts[0]);
        final int month = Integer.parseInt(dobParts[1]);
        final int day = Integer.parseInt(dobParts[2]);

        // Creating a Date object from year, month, and day
        final Date dob = new Date(year - 1900, month - 1, day);

        // Creating a new Kid object
        final Kid newKid = Kid.builder()
                .fullName(kid.getName())
                .nickName(kid.getNickname())
                .dob(dob)
                .parent(null)
                .classBelongsTo(null)
                .build();

        if (kid.getParentId() != null) {
            Parent parentFromDb = parentRepository.findById(kid.getParentId()).orElse(null);
            assert parentFromDb != null;
            parentFromDb.setKid(newKid);
            newKid.setParent(parentFromDb);
        }

        if (kid.getClassId() != null) {
            Class classFromDb = classRepository.findById(kid.getClassId()).orElse(null);
            assert classFromDb != null;
            classFromDb.getKids().add(newKid);
            newKid.setClassBelongsTo(classFromDb);
        }

        // Saving the newKid object to the repository
        kidRepository.save(newKid);

        // Returning a response indicating success
        return KidResponseDto.builder()
                .isError(false)
                .message("Kid created successfully.")
                .data(newKid)
                .build();
    }

    public KidResponseDto getKids() {
        final List<Kid> kids = kidRepository.findAll();
        return KidResponseDto.builder()
                .isError(false)
                .message("Kids fetched successfully.")
                .data(kids)
                .build();
    }
}
