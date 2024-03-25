package com.example.demo.modules.kids.entities;

import com.example.demo.modules.classes.entities.Class;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Date;

@Entity
@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class Kid {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String fullName;

    @Column(name = "nickname")
    private String nickName;

    @Column(name = "date_of_birth")
    private Date dob;

    @Column(name = "parent_name")
    private String parentName;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "class_id")
    private Class classBelongsTo;
}
