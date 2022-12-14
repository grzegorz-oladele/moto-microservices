package pl.grzegorz.motorcycleservice.motorcycle;

import pl.grzegorz.motorcycleservice.motorcycle.dto.input.MotorcycleDto;
import pl.grzegorz.motorcycleservice.motorcycle.dto.output.MotorcycleLapOutputDto;
import pl.grzegorz.motorcycleservice.motorcycle.dto.output.MotorcycleOutputDto;

import java.util.List;

public interface MotorcycleFacade {

    MotorcycleOutputDto getMotorcycleById(long motorcycleId);
    List<MotorcycleOutputDto> getListOfMotorcycles(int page, int size);
    MotorcycleLapOutputDto getMotorcycleToLap(long motorcycleId);
    void addMotorcycle(long bikerId, long bikeClassId, MotorcycleDto motorcycleDto);
    void editMotorcycle(long motorcycleId, MotorcycleDto motorcycleDto);
    void removeMotorcycle(long motorcycleId);
}
