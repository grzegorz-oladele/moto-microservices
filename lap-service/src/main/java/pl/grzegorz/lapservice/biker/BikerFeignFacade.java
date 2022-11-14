package pl.grzegorz.lapservice.biker;

import pl.grzegorz.lapservice.biker.details.BikerDetails;

public interface BikerFeignFacade {

    BikerDetails getBikerById(long bikerId);
}