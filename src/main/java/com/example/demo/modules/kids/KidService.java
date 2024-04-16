package com.example.demo.modules.kids;

import com.example.demo.modules.classes.Class;
import com.example.demo.modules.classes.ClassRepository;
import com.example.demo.modules.kids.dtos.CreateKidDto;
import com.example.demo.modules.kids.dtos.KidResponseDto;
import com.example.demo.modules.kids.dtos.KidWithClassDto;
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
                .classBelongsTo(null)
                .dob(dob)
                .build();

        if (kid.getParentId() != null) {
            Parent parentFromDb = parentRepository.findById(kid.getParentId()).orElse(null);
            if (parentFromDb != null) {
                parentFromDb.setKid(newKid);
                newKid.setParent(parentFromDb);
                 // Save the parent to update the association
            }
        }


        if (kid.getClassId() != null) {
            Class classFromDb = classRepository.findById(kid.getClassId()).orElse(null);
            if (classFromDb != null) {
                classFromDb.getKids().add(newKid);
                newKid.setClassBelongsTo(classFromDb);
                classRepository.save(classFromDb); // Save the class to update the association
            }
        }


        kidRepository.save(newKid);

        return KidResponseDto.builder()
                .isError(false)
                .message("Trẻ đã được tạo thành công.")
                .data(newKid)
                .build();
    }

    public KidResponseDto findAll() {
        final List<Kid> kids = kidRepository.findAll();
        return KidResponseDto.builder()
                .isError(false)
                .message("Thông tin của tất cả trẻ đã được tìm thấy.")
                .data(kids)
                .build();
    }

    public KidResponseDto update(Long id, CreateKidDto updateKidDto) {
        final Kid kidFromDb = kidRepository.findById(id).orElse(null);
        if (kidFromDb == null) {
            return KidResponseDto.builder()
                    .isError(true)
                    .message("Trẻ không tồn tại.")
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

        kidRepository.save(kidFromDb);
        parentRepository.save(parent);
        classRepository.save(classBelongsTo);

        return KidResponseDto.builder()
                .isError(false)
                .message("Trẻ đã được cập nhật.")
                .data(kidFromDb)
                .build();
    }

    public KidResponseDto delete(Long id) {
        final Kid kidFromDb = kidRepository.findById(id).orElse(null);
        if (kidFromDb == null) {
            return KidResponseDto.builder()
                    .isError(true)
                    .message("Trẻ không tồn tại.")
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

        kidRepository.delete(kidFromDb);
        return KidResponseDto.builder()
                .isError(false)
                .message("Xóa trẻ thành công.")
                .build();
    }

    public KidResponseDto kickKid(KidWithClassDto kidWithClassDto) {
        final Kid kidFromDb = kidRepository.findById(kidWithClassDto.getKidId()).orElse(null);
        if (kidFromDb == null) {
            return KidResponseDto.builder()
                    .isError(true)
                    .message("Trẻ không tồn tại.")
                    .build();
        }
        final Class classBelongsTo = classRepository.findById(kidWithClassDto.getClassId()).orElse(null);
        if (classBelongsTo == null) {
            return KidResponseDto.builder()
                    .isError(true)
                    .message("Lớp không tồn tại.")
                    .build();
        }

        classBelongsTo.getKids().remove(kidFromDb);
        kidFromDb.setClassBelongsTo(null);
        classRepository.save(classBelongsTo);
        kidRepository.save(kidFromDb);

        return KidResponseDto.builder()
                .isError(false)
                .message("Xóa thành công.")
                .data(kidFromDb)
                .build();
    }

    public KidResponseDto addKidToClass(KidWithClassDto kidWithClassDto) {
        final Kid kidFromDb = kidRepository.findById(kidWithClassDto.getKidId()).orElse(null);
        if (kidFromDb == null) {
            return KidResponseDto.builder()
                    .isError(true)
                    .message("Trẻ không tồn tại.")
                    .build();
        }
        final Class classBelongsTo = classRepository.findById(kidWithClassDto.getClassId()).orElse(null);
        if (classBelongsTo == null) {
            return KidResponseDto.builder()
                    .isError(true)
                    .message("Lớp không tồn tại.")
                    .build();
        }

        classBelongsTo.getKids().add(kidFromDb);
        kidFromDb.setClassBelongsTo(classBelongsTo);
        classRepository.save(classBelongsTo);
        kidRepository.save(kidFromDb);

        return KidResponseDto.builder()
                .isError(false)
                .message("Thêm thành công.")
                .data(kidFromDb)
                .build();
    }

}
