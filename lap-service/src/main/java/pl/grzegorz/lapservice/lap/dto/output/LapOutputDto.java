package pl.grzegorz.lapservice.lap.dto.output;

public interface LapOutputDto {

    String getId();

    String getLapTime();

    String getLapDate();

    BikerOutputDto getBiker();

    MotorcycleOutputDto getMotorcycle();

    CircuitOutputDto getCircuit();
}
