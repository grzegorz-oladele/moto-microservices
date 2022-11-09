package pl.grzegorz.circuitservice.track;

import pl.grzegorz.circuitservice.track.dto.input.TrackDto;
import pl.grzegorz.circuitservice.track.dto.output.TrackOutputDto;

import java.util.List;

public interface TrackFacade {

    TrackOutputDto getTrackById(long trackId);

    List<TrackOutputDto> getAllTracks(int page, int size);

    List<TrackOutputDto> getAllTracksByCircuit(long circuitId, int page, int size);

    void addTrack(long circuitId, TrackDto trackDto);

    void editTrack(long trackId, TrackDto trackDto);

    void removeTrack(long trackId);
}