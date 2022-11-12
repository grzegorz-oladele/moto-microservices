package pl.grzegorz.lapservice.lap.dto.mapper;

import lombok.*;

import static lombok.AccessLevel.PRIVATE;
import static lombok.AccessLevel.PROTECTED;

@Getter(value = PROTECTED)
@Setter(value = PROTECTED)
@AllArgsConstructor(access = PRIVATE)
@Builder
class MotorcycleDetailsOutputDto {

    private Long motorcycleId;
    private String brand;
    private String model;
    private Integer capacity;
    private Integer horsePower;
}