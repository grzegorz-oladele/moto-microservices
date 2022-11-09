package pl.grzegorz.circuitservice.circuit;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import pl.grzegorz.circuitservice.circuit.dto.input.CircuitDto;
import pl.grzegorz.circuitservice.circuit.dto.output.CircuitOutputDto;
import pl.grzegorz.circuitservice.circuit.query.CircuitSimpleEntity;

import java.util.List;

import static pl.grzegorz.circuitservice.circuit.CircuitEntity.*;

@Service
@RequiredArgsConstructor
@Slf4j
class CircuitFacadeImpl implements CircuitFacade {

    private final CircuitRepository circuitRepository;
    private final CircuitQueryRepository circuitQueryRepository;

    @Override
    public CircuitOutputDto getCircuitById(long circuitId) {
        return circuitQueryRepository.findAllById(circuitId)
                .orElseThrow(() -> new IllegalArgumentException("Circuit not found"));
    }

    @Override
    public List<CircuitOutputDto> getAllCircuits(int page, int size) {
        return circuitQueryRepository.findAllBy(PageRequest.of(page - 1, size));
    }

    @Override
    public CircuitSimpleEntity getCircuitSimpleEntityById(long circuitId) {
        CircuitEntity circuit = getCircuitEntityById(circuitId);
        return CircuitSimpleEntity.builder()
                .id(circuit.getId())
                .name(circuit.getName())
                .listOfCircuits(circuit.getListOfTracks())
                .build();
    }

    private CircuitEntity getCircuitEntityById(long circuitId) {
        return circuitRepository.findById(circuitId)
                .orElseThrow(() -> {
                    log.error("Circuit with id -> {} not found", circuitId);
                    throw new IllegalArgumentException("Circuit not found");
                });
    }

    @Override
    public void addNewCircuit(CircuitDto circuitDto) {
        CircuitEntity motorcycleTrack = toMotorcycleTrackEntity(circuitDto);
        circuitRepository.save(motorcycleTrack);
        log.info("Save new circuit with name -> {} in city -> {}", motorcycleTrack.getName(),
                motorcycleTrack.getCity());
    }

    @Override
    public void editCircuit(long circuitId, CircuitDto circuitDto) {
        checkMotorcycleTrackAndThrowExceptionIfNotExist(circuitId);
        CircuitEntity updatedMotorcycleTrack = toMotorcycleTrackEntity(circuitDto);
        updatedMotorcycleTrack.setId(circuitId);
        circuitRepository.save(updatedMotorcycleTrack);
        log.info("Update circuit with id -> {}", circuitId);
    }

    @Override
    public void removeCircuit(long circuitId) {
        checkMotorcycleTrackAndThrowExceptionIfNotExist(circuitId);
        circuitRepository.deleteById(circuitId);
        log.info("Remove circuit with id -> {}", circuitId);
    }

    private void checkMotorcycleTrackAndThrowExceptionIfNotExist(long motorcycleTrackId) {
        if (!existMotorcycleTrackById(motorcycleTrackId)) {
            log.error("Circuit with id -> {} not found", motorcycleTrackId);
            throw new IllegalArgumentException("Circuit not found");
        }
    }

    private boolean existMotorcycleTrackById(long motorcycleTrackId) {
        return circuitRepository.existsById(motorcycleTrackId);
    }
}