package pl.grzegorz.motorcycleservice.motorcycle.dto.input;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class MotorcycleDto {

    private String brand;
    private String model;
    private int capacity;
    private int horsePower;
    private int vintage;
}