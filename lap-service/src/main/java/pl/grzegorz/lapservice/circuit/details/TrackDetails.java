package pl.grzegorz.lapservice.circuit.details;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import static lombok.AccessLevel.PRIVATE;

@Getter
@AllArgsConstructor(access = PRIVATE)
@Builder
public class TrackDetails {

    private Long trackId;
    private String trackName;
    private Double trackLength;
}