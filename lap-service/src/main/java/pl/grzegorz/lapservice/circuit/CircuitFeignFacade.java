package pl.grzegorz.lapservice.circuit;

import pl.grzegorz.lapservice.circuit.details.CircuitDetails;

public interface CircuitFeignFacade {

    CircuitDetails getCircuitDetails(long trackId, long circuitId);
}
