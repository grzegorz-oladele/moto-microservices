package pl.grzegorz.circuitservice.track.dto.output;

import pl.grzegorz.circuitservice.circuit.dto.output.CircuitSimpleOutputDto;

public interface TrackOutputDto {

    Long getId();

    String getName();

    Double getTrackLength();

    Boolean getIsThereFence();

    Boolean getIsTherePlaceForAd();

    CircuitSimpleOutputDto getCircuit();
}