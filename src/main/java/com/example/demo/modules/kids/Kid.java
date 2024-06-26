package com.example.demo.modules.kids;

import com.example.demo.modules.classes.Class;
import com.example.demo.modules.parents.Parent;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Date;
import java.util.List;

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

    @OneToOne(cascade = CascadeType.PERSIST)
    @JsonIgnoreProperties("kid")
    private Parent parent;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "class_id")
    private Class classBelongsTo;

}
