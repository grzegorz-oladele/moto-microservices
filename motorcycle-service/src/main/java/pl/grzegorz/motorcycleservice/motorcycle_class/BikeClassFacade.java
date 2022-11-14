package pl.grzegorz.motorcycleservice.motorcycle_class;

import pl.grzegorz.motorcycleservice.motorcycle_class.dto.input.BikeClassDto;
import pl.grzegorz.motorcycleservice.motorcycle_class.dto.output.BikeClassOutputDto;
import pl.grzegorz.motorcycleservice.motorcycle_class.query.BikeClassSimpleEntity;

import java.util.List;

public interface BikeClassFacade {

    List<BikeClassOutputDto> getMotorcycleClassList(int page, int size);
    BikeClassOutputDto getMotorcycleClassById(long motorcycleClassId);
    BikeClassSimpleEntity getBikeClassSimpleEntity(long bikeClassId);
    void addMotorcyclesClass(BikeClassDto bikeClassDto);
    void editMotorcycleClass(long motorcycleClassId, BikeClassDto bikeClassDto);
    void removeMotorcycleClassById(long motorcycleClassId);
}
