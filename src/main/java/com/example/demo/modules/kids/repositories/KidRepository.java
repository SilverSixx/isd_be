package com.example.demo.modules.kids.repositories;

import com.example.demo.modules.kids.entities.Kid;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KidRepository extends JpaRepository<Kid, Long> {
}
