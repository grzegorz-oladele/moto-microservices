package pl.grzegorz.motorcycleservice.motorcycle_class.dto.input;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
@Builder
@AllArgsConstructor
public class BikeClassDto {

    @NotBlank(message = "Name filed must not be null/empty")
    private String name;
    @NotBlank(message = "Description filed must not be null/empty")
    private String description;
}
