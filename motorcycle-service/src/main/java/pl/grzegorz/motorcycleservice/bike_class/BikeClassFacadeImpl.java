package pl.grzegorz.motorcycleservice.bike_class;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import pl.grzegorz.motorcycleservice.bike_class.dto.input.BikeClassDto;
import pl.grzegorz.motorcycleservice.bike_class.dto.output.BikeClassOutputDto;
import pl.grzegorz.motorcycleservice.bike_class.query.BikeClassSimpleEntity;
import pl.grzegorz.motorcycleservice.tools.validator.ValidatorFacade;

import java.util.List;

import static pl.grzegorz.motorcycleservice.bike_class.BikeClassEntity.toEntity;

@Service
@RequiredArgsConstructor
@Slf4j
class BikeClassFacadeImpl implements BikeClassFacade {

    private static final String MOTORCYCLE_CLASS_NOT_FOUND_MESSAGE = "Motorcycle class not found";
    private final BikeClassRepository bikeClassRepository;
    private final BikeClassQueryRepository bikeClassQueryRepository;
    private final ValidatorFacade validatorFacade;

    @Override
    public List<BikeClassOutputDto> getMotorcycleClassList(int page, int size) {
        validatorFacade.checkPageAndSizeValueAndThrowExceptionIfIsWrong(page, size);
        return bikeClassQueryRepository.findAllBy(PageRequest.of(page - 1, size));
    }

    @Override
    public BikeClassOutputDto getMotorcycleClassById(long motorcycleClassId) {
        return bikeClassQueryRepository.findAllById(motorcycleClassId)
                .orElseThrow(() -> {
                    log.error("Motorcycle class with id -> {} not found", motorcycleClassId);
                    throw new IllegalArgumentException(MOTORCYCLE_CLASS_NOT_FOUND_MESSAGE);
                });
    }

    @Override
    public BikeClassSimpleEntity getBikeClassSimpleEntity(long bikeClassId) {
        BikeClassEntity bikeClass = getBikeClassEntityById(bikeClassId);
        log.info("Creating BikeClassSimpleEntity for bike class with id -> {}", bikeClassId);
        return BikeClassSimpleEntity.builder()
                .id(bikeClass.getId())
                .name(bikeClass.getName())
                .build();
    }

    private BikeClassEntity getBikeClassEntityById(long bikeClassId) {
        return bikeClassRepository.findById(bikeClassId)
                .orElseThrow(() -> {
                    log.error("Bike class with id -> {} not found", bikeClassId);
                    throw new IllegalArgumentException("Bike class not found");
                });
    }

    @Override
    public void addMotorcyclesClass(BikeClassDto bikeClassDto) {
        BikeClassEntity motorcycleClass = toEntity(bikeClassDto);
        bikeClassRepository.save(motorcycleClass);
        log.info("Saving new motorcycle class with name -> {}", motorcycleClass.getName());
    }

    @Override
    public void editMotorcycleClass(long motorcycleClassId, BikeClassDto bikeClassDto) {
        BikeClassEntity motorcycleClass = getMotorcycleClassEntityById(motorcycleClassId);
        BikeClassEntity updatedMotorcycleClass = toEntity(bikeClassDto);
        updatedMotorcycleClass.setId(motorcycleClass.getId());
        bikeClassRepository.save(updatedMotorcycleClass);
        log.info("Updating motorcycle class with id -> {}", motorcycleClassId);
    }

    private BikeClassEntity getMotorcycleClassEntityById(long motorcycleClassId) {
        return bikeClassRepository.findById(motorcycleClassId)
                .orElseThrow(() -> {
                    log.error("Motorcycle class with id -> {} not found", motorcycleClassId);
                    throw new IllegalArgumentException(MOTORCYCLE_CLASS_NOT_FOUND_MESSAGE);
                });
    }

    @Override
    public void removeMotorcycleClassById(long motorcycleClassId) {
        BikeClassEntity motorcycleClass = getMotorcycleClassEntityById(motorcycleClassId);
        bikeClassRepository.delete(motorcycleClass);
        log.info("Removing motorcycle class with id -> {}", motorcycleClassId);
    }
}