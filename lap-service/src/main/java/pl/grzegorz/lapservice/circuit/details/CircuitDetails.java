package pl.grzegorz.lapservice.circuit.details;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import static lombok.AccessLevel.PRIVATE;

@Getter
@AllArgsConstructor(access = PRIVATE)
@Builder
public class CircuitDetails {

    private Long circuitId;
    private String circuitName;
    private TrackDetails track;
}