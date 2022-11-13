package pl.grzegorz.lapservice.lap;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import pl.grzegorz.lapservice.lap.feign.biker.BikerDetails;

@FeignClient(name = "biker-service")
interface BikerFeignClient {

    @GetMapping("/api/bikers/{bikerId}")
    BikerDetails getBikerById(@PathVariable long bikerId);
}