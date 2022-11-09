package pl.grzegorz.circuitservice.circuit.dto.output;

import pl.grzegorz.circuitservice.track.dto.output.TrackSimpleOutputDto;

import java.util.List;

public interface CircuitOutputDto {

    Long getId();

    String getName();

    String getDescription();

    String getCity();

    String getPostalCode();

    String getStreet();

    String getStreetNumber();

    String getEmail();

    String getPhoneNumber();

    List<TrackSimpleOutputDto> getListOfTracks();
}
