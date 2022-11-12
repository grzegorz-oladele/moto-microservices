package pl.grzegorz.lapservice.lap.dto.output;

interface CircuitOutputDto {

    Long getId();

    String getName();

    TrackOutputDto getTrack();
}