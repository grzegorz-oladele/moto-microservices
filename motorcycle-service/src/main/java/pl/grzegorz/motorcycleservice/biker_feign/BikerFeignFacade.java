package pl.grzegorz.motorcycleservice.biker_feign;

public interface BikerFeignFacade {

    Boolean checkBikerExists(long bikerId);
}
