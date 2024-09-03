package com.example.demo.model.dto.request;

import com.example.demo.model.enums.Colour;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CarInfoRequest {
    String brand;
    String type;
    Integer year;
    Integer doors;
    Double capacity;
    Double weight;
    Double speed;
    Colour colour;
}
