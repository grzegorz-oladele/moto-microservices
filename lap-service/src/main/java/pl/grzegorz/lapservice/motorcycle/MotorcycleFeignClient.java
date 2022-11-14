package pl.grzegorz.lapservice.motorcycle;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import pl.grzegorz.lapservice.motorcycle.details.MotorcycleDetails;

@FeignClient(name = "motorcycle-service")
interface MotorcycleFeignClient {

    @GetMapping("/api/motorcycles/{motorcycleId}")
    MotorcycleDetails getMotorcycleById(@PathVariable long motorcycleId);
}
