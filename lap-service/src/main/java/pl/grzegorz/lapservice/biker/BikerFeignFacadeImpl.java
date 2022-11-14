package pl.grzegorz.lapservice.biker;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.grzegorz.lapservice.biker.details.BikerDetails;

@Service
@RequiredArgsConstructor
@Slf4j
class BikerFeignFacadeImpl implements BikerFeignFacade {

    private final BikerFeignClient bikerFeignClient;

    @Override
    public BikerDetails getBikerById(long bikerId) {
        return bikerFeignClient.getBikerById(bikerId);
    }
}