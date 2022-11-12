package pl.grzegorz.lapservice.lap.dto.output;

interface MotorcycleOutputDto {

    Long getMotorcycleId();

    String getBrand();

    String getModel();

    Integer getCapacity();

    Integer getHorsePower();
}