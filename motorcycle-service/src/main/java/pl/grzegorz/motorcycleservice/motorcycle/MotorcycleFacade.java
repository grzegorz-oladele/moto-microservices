package pl.grzegorz.motorcycleservice.motorcycle;

public interface MotorcycleFacade {

    MotorcycleOutputDto getMotorcycleById(long motorcycleId);

    void AddMotorcycle(MotorcycleDto motorcycleDto);
}
