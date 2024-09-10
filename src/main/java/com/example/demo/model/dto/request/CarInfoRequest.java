package com.example.demo.model.dto.request;

import com.example.demo.model.enums.Colour;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CarInfoRequest {
    @NotEmpty
    String brand;
    String model;
    Integer year;
    Integer doors;
    Double capacity;
    Double weight;
    Double speed;
    Colour colour;
}
