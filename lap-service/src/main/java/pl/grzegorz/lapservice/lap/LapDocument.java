package pl.grzegorz.lapservice.lap;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import pl.grzegorz.lapservice.biker.details.BikerDetails;
import pl.grzegorz.lapservice.motorcycle.details.MotorcycleDetails;
import pl.grzegorz.lapservice.circuit.details.CircuitDetails;
import pl.grzegorz.lapservice.lap.dto.input.LapDto;

import java.time.LocalDate;

import static lombok.AccessLevel.PRIVATE;
import static lombok.AccessLevel.PROTECTED;

@Document("laps")
@Getter
@Setter(value = PROTECTED)
@AllArgsConstructor(access = PRIVATE)
@Builder
class LapDocument {

    @Id
    private String id;
    private String lapTime;
    private LocalDate lapDate;
    private BikerDetails bikerDetails;
    private MotorcycleDetails motorcycleDetails;
    private CircuitDetails circuitDetails;

    static LapDocument toLapDocument(LapDto lapDto) {
        return LapDocument.builder()
                .lapTime(lapDto.getLapTime())
                .lapDate(LocalDate.now())
                .build();
    }
}