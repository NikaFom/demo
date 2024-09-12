package com.example.demo.model.db.repository;

import com.example.demo.model.db.entity.CarEntity;
import com.example.demo.model.enums.CarStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;

@Repository
public interface CarRepository extends JpaRepository<CarEntity, Long> {
    Page<CarEntity> findAllByStatusNot(Pageable request, CarStatus status);
}
