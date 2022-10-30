package pl.grzegorz.motorcycleservice.motorcycle.dto.input;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.Locale;

@Getter
@AllArgsConstructor
@Builder
public class MotorcycleDto {

    @NotBlank(message = "Brand field must not be null/empty")
    private String brand;
    @NotBlank(message = "Model field must not be null.empty")
    private String model;
    @Min(value = 1, message = "Capacity value must be greater than 0")
    @Max(value = 8000, message = "Capacity value must be less than 8000")
    private int capacity;
    @Min(value = 1, message = "Horse power value must be greater than 0")
    @Max(value = 500, message = "Horse power value must be less than 500")
    private int horsePower;
    @Min(value = 1900, message = "Vintage value must be greater or equal 1900")
    private int vintage;
}
