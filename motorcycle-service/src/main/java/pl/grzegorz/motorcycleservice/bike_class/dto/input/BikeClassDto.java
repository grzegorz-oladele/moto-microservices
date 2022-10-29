package pl.grzegorz.motorcycleservice.bike_class.dto.input;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class BikeClassDto {

    private String name;
    private String description;
}
