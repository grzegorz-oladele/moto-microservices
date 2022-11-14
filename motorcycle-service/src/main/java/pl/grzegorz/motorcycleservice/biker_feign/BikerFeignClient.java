package pl.grzegorz.motorcycleservice.biker_feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "biker-service")
interface BikerFeignClient {

    @GetMapping("/api/bikers/{bikerId}/exists")
    Boolean checkBikerExists(@PathVariable long bikerId);
}
