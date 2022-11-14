package pl.grzegorz.lapservice.circuit;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import pl.grzegorz.lapservice.circuit.details.CircuitDetails;

@FeignClient(name = "circuit-service")
interface CircuitFeignClient {

    @GetMapping("/api/tracks/{trackId}/laps")
    CircuitDetails getCircuitDetails(@PathVariable long trackId);
}