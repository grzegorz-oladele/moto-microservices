package pl.grzegorz.circuitservice.track.dto.input;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import static lombok.AccessLevel.PRIVATE;

@Getter
@AllArgsConstructor(access = PRIVATE)
@Builder
public class TrackDto {

    private String name;
    private double trackLength;
    private Boolean isThereFence;
    private Boolean isTherePlaceForAd;
}
