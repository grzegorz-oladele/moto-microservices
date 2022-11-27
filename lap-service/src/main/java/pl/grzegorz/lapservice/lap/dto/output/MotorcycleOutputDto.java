package pl.grzegorz.lapservice.lap.dto.output;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import static lombok.AccessLevel.PRIVATE;

@Getter
@AllArgsConstructor(access = PRIVATE)
@Builder
public class MotorcycleOutputDto {

    private Long motorcycleId;
    private String brand;
    private String model;
    private int capacity;
    private int horsePower;
    private int vintage;
}