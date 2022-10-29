package pl.grzegorz.motorcycleservice.motorcycle.dto.output;

import pl.grzegorz.motorcycleservice.bike_class.dto.output.BasicBikeClassOutputDto;
import pl.grzegorz.motorcycleservice.bike_class.dto.output.BikeClassOutputDto;

public interface MotorcycleOutputDto {

    Long getId();

    String getBrand();

    String getModel();

    Integer getCapacity();

    Integer getHorsePower();

    Integer getVintage();

    BikeClassOutputDto getMotorcycleClass();

    String getBikerId();
}
