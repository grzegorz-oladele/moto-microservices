package pl.grzegorz.motorcycleservice.motorcycle;

import pl.grzegorz.motorcycleservice.motorcycle_class.dto.output.MotorcycleClassOutputDto;

interface MotorcycleOutputDto {

    Long getId();

    String getBrand();

    String getModel();

    int getCapacity();

    int getHorsePower();

    int getVintage();

    MotorcycleClassOutputDto getMotorcycleClass();

    String getBikerId();
}
