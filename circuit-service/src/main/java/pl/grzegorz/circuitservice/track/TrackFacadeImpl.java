package pl.grzegorz.circuitservice.track;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.grzegorz.circuitservice.circuit.CircuitFacade;
import pl.grzegorz.circuitservice.circuit.query.CircuitSimpleEntity;
import pl.grzegorz.circuitservice.track.dto.input.TrackDto;
import pl.grzegorz.circuitservice.track.dto.output.TrackOutputDto;

import java.util.List;

import static org.springframework.data.domain.PageRequest.of;
import static pl.grzegorz.circuitservice.track.TrackEntity.*;

@Service
@RequiredArgsConstructor
@Slf4j
class TrackFacadeImpl implements TrackFacade {

    private final TrackRepository trackRepository;
    private final TrackQueryRepository trackQueryRepository;
    private final CircuitFacade circuitFacade;

    @Override
    public TrackOutputDto getTrackById(long trackId) {
        return trackQueryRepository.findAllById(trackId)
                .orElseThrow(() -> {
                    log.error("Track with id -> {} not found", trackId);
                    throw new IllegalArgumentException("Track not found");
                });
    }

    @Override
    public List<TrackOutputDto> getAllTracks(int page, int size) {
        return trackQueryRepository.findAllBy(of(page - 1, size));
    }

    @Override
    public List<TrackOutputDto> getAllTracksByCircuit(long circuitId, int page, int size) {
        return trackQueryRepository.findAllByCircuit_Id(circuitId, of(page - 1, size));
    }

    @Override
    public void addTrack(long circuitId, TrackDto trackDto) {
        CircuitSimpleEntity circuit = circuitFacade.getCircuitSimpleEntityById(circuitId);
        TrackEntity track = toTrackEntity(trackDto);
        track.setCircuit(circuit);
        trackRepository.save(track);
        log.info("Save new track with name -> {} in circuit with id -> {} and name -> {}", track.getName(),
                circuit.getId(), circuit.getName());
    }

    @Override
    public void editTrack(long trackId, TrackDto trackDto) {
        checkTrackAndThrowExceptionIfNotExist(trackId);
        TrackEntity track = toTrackEntity(trackDto);
        track.setId(trackId);
        trackRepository.save(track);
        log.info("Edit track with id -> {}", trackId);
    }

    @Override
    public void removeTrack(long trackId) {
        checkTrackAndThrowExceptionIfNotExist(trackId);
        trackRepository.deleteById(trackId);
        log.info("Remove track with id -> {}", trackId);
    }

    private void checkTrackAndThrowExceptionIfNotExist(long trackId) {
        if (!existTrackById(trackId)) {
            log.error("Track with id -> {} not exist", trackId);
            throw new IllegalArgumentException("Track not exist");
        }
    }

    private boolean existTrackById(long trackId) {
        return trackRepository.existsById(trackId);
    }
}