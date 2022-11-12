package pl.grzegorz.lapservice.lap;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import static lombok.AccessLevel.PROTECTED;

@Getter(value = PROTECTED)
@Setter(value = PROTECTED)
@AllArgsConstructor(access = PROTECTED)
@Builder
class Motorcycle {

    private Long motorcycleId;
    private String brand;
    private String model;
    private Integer capacity;
    private Integer horsePower;
}