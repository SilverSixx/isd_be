package com.example.demo.modules.classes.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.modules.classes.entities.Class;

public interface ClassRepository extends JpaRepository<Class, Long> {
}
