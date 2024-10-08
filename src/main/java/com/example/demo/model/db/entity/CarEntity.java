package com.example.demo.model.db.entity;

import com.example.demo.model.enums.CarStatus;
import com.example.demo.model.enums.Colour;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "cars")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CarEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "brand")
    String brand;

    @Column(name = "model")
    String model;

    @Column(name = "year")
    Integer year;

    @Column(name = "doors")
    Integer doors;

    @Column(name = "capacity")
    Double capacity;

    @Column(name = "weight")
    Double weight;

    @Column(name = "speed")
    Double speed;

    @Column(name = "colour")
    @Enumerated(EnumType.STRING)
    Colour colour;

    @Column(name = "created_at")
    LocalDateTime createdAt;

    @Column(name = "updated_at")
    LocalDateTime updatedAt;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    CarStatus status;

    @ManyToOne
    @JsonBackReference(value = "driver_cars")
    UserEntity user;
}
