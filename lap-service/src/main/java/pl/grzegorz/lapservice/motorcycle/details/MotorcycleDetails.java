package pl.grzegorz.lapservice.motorcycle.details;

import lombok.*;

import static lombok.AccessLevel.*;

@Getter
@AllArgsConstructor(access = PRIVATE)
@Builder
public class MotorcycleDetails {

    private Long id;
    private String brand;
    private String model;
    private Integer capacity;
    private Integer horsePower;
}