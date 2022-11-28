package pl.grzegorz.lapservice.lap.dto.output;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import pl.grzegorz.lapservice.circuit.details.CircuitDetails;
import pl.grzegorz.lapservice.motorcycle.details.MotorcycleDetails;

import java.time.LocalDate;

import static lombok.AccessLevel.PRIVATE;

@Getter
@AllArgsConstructor(access = PRIVATE)
@Builder
public class LapDetailsByBiker {

    private String id;
    private String lapTime;
    private LocalDate lapDate;
    private MotorcycleDetails motorcycleDetails;
    private CircuitDetails circuitDetails;
}