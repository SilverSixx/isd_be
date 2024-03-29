package com.example.demo.modules.parents;

import com.example.demo.modules.kids.Kid;
import com.example.demo.modules.kids.KidRepository;
import com.example.demo.modules.parents.dtos.CreateParentDto;
import com.example.demo.modules.parents.dtos.ParentResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ParentService {
    private final ParentRepository parentRepository;
    private final KidRepository kidRepository;
    public ParentResponseDto create(CreateParentDto createParentDto) {
        Parent parent = Parent.builder()
                .fullName(createParentDto.getFullName())
                .username(createParentDto.getUsername())
                .password(createParentDto.getPassword())
                .kid(null)
                .build();
        if(createParentDto.getKidId() != null) {
            Kid kid = kidRepository.findById(createParentDto.getKidId()).orElse(null);
            assert kid != null;
            kid.setParent(parent);
            parent.setKid(kid);
        }
        parentRepository.save(parent);
        return ParentResponseDto.builder()
                .isError(false)
                .message("Parent created successfully.")
                .data(parent)
                .build();
    }

    public ParentResponseDto getParents() {
        return ParentResponseDto.builder()
                .isError(false)
                .message("Parents fetched successfully.")
                .data(parentRepository.findAll())
                .build();
    }

    public ParentResponseDto delete(Long id) {
        final Parent parent = parentRepository.findById(id).orElse(null);
        assert parent != null;

        final Kid kid = parent.getKid();
        if (kid != null) {
            kid.setParent(null);
            parent.setKid(null);
            kidRepository.save(kid);
        }

        parentRepository.deleteById(id);
        return ParentResponseDto.builder()
                .isError(false)
                .message("Parent deleted successfully.")
                .data(null)
                .build();
    }
}
