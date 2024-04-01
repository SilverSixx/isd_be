package com.example.demo.modules.food;

import com.example.demo.modules.kids.Kid;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class Food {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fid")
    private Long id;

    private String name;
    private Double totalAmount;

    @ManyToMany(mappedBy = "allergyFoods")
    @JsonIgnoreProperties("allergyFoods")
    private List<Kid> allergyKids;
}
