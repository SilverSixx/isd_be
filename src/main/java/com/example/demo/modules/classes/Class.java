package com.example.demo.modules.classes;

import com.example.demo.modules.kids.Kid;
import com.example.demo.modules.teachers.Teacher;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Entity
@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class Class {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cid")
    private Long id;

    @Column(unique = true)
    private String name;

    private Integer grade;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;

    @JsonIgnoreProperties("classBelongsTo")
    @OneToMany(cascade = CascadeType.MERGE, mappedBy = "classBelongsTo")
    private List<Kid> kids;
}
