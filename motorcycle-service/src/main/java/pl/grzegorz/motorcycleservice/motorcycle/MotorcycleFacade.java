package pl.grzegorz.motorcycleservice.motorcycle;

import pl.grzegorz.motorcycleservice.motorcycle.dto.input.MotorcycleDto;
import pl.grzegorz.motorcycleservice.motorcycle.dto.output.MotorcycleOutputDto;

import java.util.List;

public interface MotorcycleFacade {

    MotorcycleOutputDto getMotorcycleById(long motorcycleId);
    List<MotorcycleOutputDto> getListOfMotorcycles();
    void AddMotorcycle(MotorcycleDto motorcycleDto, long bikeClassId);
    void editMotorcycle(long motorcycleId, MotorcycleDto motorcycleDto);
    void removeMotorcycle(long motorcycleId);
}
