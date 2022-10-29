package pl.grzegorz.motorcycleservice.bike_class;

import pl.grzegorz.motorcycleservice.bike_class.dto.input.BikeClassDto;
import pl.grzegorz.motorcycleservice.bike_class.dto.output.BikeClassOutputDto;
import pl.grzegorz.motorcycleservice.bike_class.query.BikeClassSimpleEntity;

import java.util.List;

public interface BikeClassFacade {

    List<BikeClassOutputDto> getMotorcycleClassList();
    BikeClassOutputDto getMotorcycleClassById(long motorcycleClassId);
    BikeClassSimpleEntity getBikeClassSimpleEntity(long bikeClassId);
    void addMotorcyclesClass(BikeClassDto bikeClassDto);
    void editMotorcycleClass(long motorcycleClassId, BikeClassDto bikeClassDto);
    void removeMotorcycleClassById(long motorcycleClassId);
}
