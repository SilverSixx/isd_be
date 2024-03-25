package com.example.demo.modules.classes.entities;

import com.example.demo.modules.kids.entities.Kid;
import com.example.demo.modules.teachers.entities.Teacher;
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

    private String name;

    private String type;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "teacher_id", nullable = false)
    private Teacher teacher;

    @OneToMany(cascade = CascadeType.MERGE, mappedBy = "classBelongsTo")
    private List<Kid> kids;
}
