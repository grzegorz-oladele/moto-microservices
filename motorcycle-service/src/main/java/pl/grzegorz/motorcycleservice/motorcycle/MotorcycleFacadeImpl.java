package pl.grzegorz.motorcycleservice.motorcycle;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import pl.grzegorz.motorcycleservice.biker_feign.BikerFeignFacade;
import pl.grzegorz.motorcycleservice.motorcycle.dto.output.MotorcycleLapOutputDto;
import pl.grzegorz.motorcycleservice.motorcycle_class.BikeClassFacade;
import pl.grzegorz.motorcycleservice.motorcycle_class.query.BikeClassSimpleEntity;
import pl.grzegorz.motorcycleservice.motorcycle.dto.input.MotorcycleDto;
import pl.grzegorz.motorcycleservice.motorcycle.dto.output.MotorcycleOutputDto;
import pl.grzegorz.motorcycleservice.tools.validator.ValidatorFacade;

import java.util.List;

import static java.lang.Boolean.FALSE;
import static pl.grzegorz.motorcycleservice.motorcycle.MotorcycleEntity.toEntity;
import static pl.grzegorz.motorcycleservice.motorcycle.MotorcycleMapper.*;

@Service
@RequiredArgsConstructor
@Slf4j
class MotorcycleFacadeImpl implements MotorcycleFacade {

    private final MotorcycleRepository motorcycleRepository;
    private final MotorcycleQueryRepository motorcycleQueryRepository;
    private final BikeClassFacade bikeClassFacade;
    private final ValidatorFacade validatorFacade;
    private final BikerFeignFacade bikerFeignFacade;

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
    public MotorcycleLapOutputDto getMotorcycleToLap(long motorcycleId) {
        MotorcycleEntity motorcycle = getMotorcycleEntityById(motorcycleId);
        return toMotorcycleLapOutputDto(motorcycle);
    }

    @Override
    public void addMotorcycle(long bikerId, long bikeClassId, MotorcycleDto motorcycleDto) {
        validatorFacade.checkVintageValueAndThrowExceptionIfIsWrong(motorcycleDto.getVintage());
        MotorcycleEntity motorcycle = toEntity(motorcycleDto);
        BikeClassSimpleEntity bikeClass = bikeClassFacade.getBikeClassSimpleEntity(bikeClassId);
        motorcycle.setMotorcycleClass(bikeClass);
        checkBikerExistAndThrowExceptionIfNot(bikerId);
        motorcycle.setBikerId(bikerId);
        motorcycleRepository.save(motorcycle);
    }

    private void checkBikerExistAndThrowExceptionIfNot(long bikerId) {
        if (checkBikerExists(bikerId).equals(FALSE)) {
            log.error("Biker with id -> {} not exist", bikerId);
            throw new IllegalArgumentException("Biker not exist");
        }
    }

    private Boolean checkBikerExists(long bikerId) {
        return bikerFeignFacade.checkBikerExists(bikerId);
    }

    @Override
    public void editMotorcycle(long motorcycleId, MotorcycleDto motorcycleDto) {
        validatorFacade.checkVintageValueAndThrowExceptionIfIsWrong(motorcycleDto.getVintage());
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