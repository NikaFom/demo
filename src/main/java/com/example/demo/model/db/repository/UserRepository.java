package com.example.demo.model.db.repository;

import com.example.demo.model.db.entity.CarEntity;
import com.example.demo.model.db.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository <UserEntity, Long> {
}
