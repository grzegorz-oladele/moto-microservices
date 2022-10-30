package pl.grzegorz.motorcycleservice.motorcycle;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import pl.grzegorz.motorcycleservice.bike_class.BikeClassFacade;
import pl.grzegorz.motorcycleservice.bike_class.query.BikeClassSimpleEntity;
import pl.grzegorz.motorcycleservice.motorcycle.dto.input.MotorcycleDto;
import pl.grzegorz.motorcycleservice.motorcycle.dto.output.MotorcycleOutputDto;
import pl.grzegorz.motorcycleservice.tools.validator.ValidatorFacade;

import java.util.List;

import static pl.grzegorz.motorcycleservice.motorcycle.MotorcycleEntity.toEntity;

@Service
@RequiredArgsConstructor
@Slf4j
class MotorcycleFacadeImpl implements MotorcycleFacade {

    private final MotorcycleRepository motorcycleRepository;
    private final MotorcycleQueryRepository motorcycleQueryRepository;
    private final BikeClassFacade bikeClassFacade;
    private final ValidatorFacade validatorFacade;

    @Override
    public MotorcycleOutputDto getMotorcycleById(long motorcycleId) {
        return motorcycleQueryRepository.findAllById(motorcycleId)
                .orElseThrow(() -> {
                    log.error("Motorcycle wit id -> {} not found", motorcycleId);
                    throw new IllegalArgumentException("Motorcycle not found");
                });
    }

    @Override
    public List<MotorcycleOutputDto> getListOfMotorcycles(int page, int size) {
        validatorFacade.checkPageAndSizeValueAndThrowExceptionIfIsWrong(page, size);
        return motorcycleQueryRepository.findAllBy(PageRequest.of(page - 1, size));
    }

    @Override
    public void addMotorcycle(MotorcycleDto motorcycleDto, long bikeClassId) {
        MotorcycleEntity motorcycle = toEntity(motorcycleDto);
        BikeClassSimpleEntity bikeClass = bikeClassFacade.getBikeClassSimpleEntity(bikeClassId);
        motorcycle.setMotorcycleClass(bikeClass);
        motorcycleRepository.save(motorcycle);
    }

    @Override
    public void editMotorcycle(long motorcycleId, MotorcycleDto motorcycleDto) {
        MotorcycleEntity motorcycle = getMotorcycleEntityById(motorcycleId);
        MotorcycleEntity updatedMotorcycle = toEntity(motorcycleDto);
        updatedMotorcycle.setId(motorcycle.getId());
        motorcycleRepository.save(updatedMotorcycle);
        log.info("Updating motorcycle with id -> {}", motorcycleId);
    }

    @Override
    public void removeMotorcycle(long motorcycleId) {
        MotorcycleEntity motorcycle = getMotorcycleEntityById(motorcycleId);
        motorcycleRepository.delete(motorcycle);
        log.info("Removing motorcycle with id -> {}", motorcycleId);
    }

    private MotorcycleEntity getMotorcycleEntityById(long motorcycleId) {
        return motorcycleRepository.findById(motorcycleId)
                .orElseThrow(() -> {
                    log.error("Motorcycle with id -> {} not found", motorcycleId);
                    throw new IllegalArgumentException("Motorcycle not found");
                });
    }
}