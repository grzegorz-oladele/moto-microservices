package pl.grzegorz.motorcycleservice.biker_feign;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class BikerFeignFacadeImpl implements BikerFeignFacade {

    private final BikerFeignClient bikerFeignClient;

    @Override
    public Boolean checkBikerExists(long bikerId) {
        return bikerFeignClient.checkBikerExists(bikerId);
    }
}
