package pl.grzegorz.lapservice.lap.dto.output;

import java.time.LocalDate;

public interface LapByCandidateOutputDto {

    String getId();

    String getLapTime();

    LocalDate getLapDate();

    MotorcycleOutputDto getMotorcycle();

    CircuitOutputDto getCircuit();
}