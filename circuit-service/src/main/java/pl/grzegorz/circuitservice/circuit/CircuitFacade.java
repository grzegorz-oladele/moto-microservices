package pl.grzegorz.circuitservice.circuit;

import pl.grzegorz.circuitservice.circuit.dto.output.CircuitOutputDto;
import pl.grzegorz.circuitservice.circuit.dto.input.CircuitDto;
import pl.grzegorz.circuitservice.circuit.query.CircuitSimpleEntity;

import java.util.List;

public interface CircuitFacade {

    CircuitOutputDto getCircuitById(long circuitId);

    List<CircuitOutputDto> getAllCircuits(int page, int size);
    CircuitSimpleEntity getCircuitSimpleEntityById(long circuitId);

    void addNewCircuit(CircuitDto circuitDto);

    void editCircuit(long circuitId, CircuitDto circuitDto);

    void removeCircuit(long circuitId);
}
