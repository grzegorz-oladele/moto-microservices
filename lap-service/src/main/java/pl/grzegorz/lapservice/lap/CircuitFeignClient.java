package pl.grzegorz.lapservice.lap;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import pl.grzegorz.lapservice.lap.feign.track.CircuitDetails;

@FeignClient(name = "circuit-service")
interface CircuitFeignClient {

    @GetMapping("/api/tracks/{trackId}/circuits/{circuitId}")
    CircuitDetails getCircuitDetails(@PathVariable long trackId, @PathVariable long circuitId);
}
