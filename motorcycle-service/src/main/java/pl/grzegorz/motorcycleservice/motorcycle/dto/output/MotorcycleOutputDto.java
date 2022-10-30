package pl.grzegorz.motorcycleservice.motorcycle.dto.output;

import pl.grzegorz.motorcycleservice.bike_class.dto.output.BasicBikeClassOutputDto;

public interface MotorcycleOutputDto {

    Long getId();

    String getBrand();

    String getModel();

    Integer getCapacity();

    Integer getHorsePower();

    Integer getVintage();

    BasicBikeClassOutputDto getMotorcycleClass();

    String getBikerId();
}
