package pl.grzegorz.motorcycleservice.motorcycle.dto.output;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import static lombok.AccessLevel.PRIVATE;

@Getter
@AllArgsConstructor(access = PRIVATE)
@Builder
public class MotorcycleLapOutputDto {

    private Long motorcycleId;
    private String brand;
    private String model;
    private Integer capacity;
    private Integer horsePower;
    private Long bikerId;
}