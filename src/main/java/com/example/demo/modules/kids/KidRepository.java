package com.example.demo.modules.kids;

import com.example.demo.modules.kids.Kid;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KidRepository extends JpaRepository<Kid, Long> {
}
