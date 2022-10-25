package pl.grzegorz.motorcycleservice.motorcycle_class;

import pl.grzegorz.motorcycleservice.motorcycle_class.dto.input.MotorcycleClassDto;
import pl.grzegorz.motorcycleservice.motorcycle_class.dto.output.MotorcycleClassOutputDto;

import java.util.List;

public interface MotorcycleClassFacade {

    List<MotorcycleClassOutputDto> getMotorcycleClassList();
    MotorcycleClassOutputDto getMotorcycleClassById(long motorcycleClassId);
    void addMotorcyclesClass(MotorcycleClassDto motorcycleClassDto);
    void editMotorcycleClass(long motorcycleClassId, MotorcycleClassDto motorcycleClassDto);
    void removeMotorcycleClassById(long motorcycleClassId);
}
