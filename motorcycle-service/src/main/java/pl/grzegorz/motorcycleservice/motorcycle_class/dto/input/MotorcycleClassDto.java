package pl.grzegorz.motorcycleservice.motorcycle_class.dto.input;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class MotorcycleClassDto {

    private String name;
    private String description;
}
