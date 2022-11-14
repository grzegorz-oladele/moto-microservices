package pl.grzegorz.lapservice.circuit;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.grzegorz.lapservice.circuit.details.CircuitDetails;

@Service
@RequiredArgsConstructor
@Slf4j
class CircuitFeignFacadeImpl implements CircuitFeignFacade {

    private final CircuitFeignClient circuitFeignClient;

    @Override
    public CircuitDetails getCircuitDetails(long trackId, long circuitId) {
        return circuitFeignClient.getCircuitDetails(trackId, circuitId);
    }
}
