package pl.grzegorz.motorcycleservice.motorcycle_class;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.grzegorz.motorcycleservice.motorcycle_class.dto.input.MotorcycleClassDto;
import pl.grzegorz.motorcycleservice.motorcycle_class.dto.output.MotorcycleClassOutputDto;

import java.util.List;

import static pl.grzegorz.motorcycleservice.motorcycle_class.MotorcycleClassEntity.*;

@Service
@RequiredArgsConstructor
@Slf4j
class MotorcycleClassFacadeImpl implements MotorcycleClassFacade {

    private static final String MOTORCYCLE_CLASS_NOT_FOUND_MESSAGE = "Motorcycle class not found";
    private final MotorcycleClassRepository motorcycleClassRepository;
    private final MotorcycleClassQueryRepository motorcycleClassQueryRepository;

    @Override
    public List<MotorcycleClassOutputDto> getMotorcycleClassList() {
        return motorcycleClassQueryRepository.findAllBy();
    }

    @Override
    public MotorcycleClassOutputDto getMotorcycleClassById(long motorcycleClassId) {
        return motorcycleClassQueryRepository.findAllById(motorcycleClassId)
                .orElseThrow(() -> {
                    log.error("Motorcycle class with id -> {} not found", motorcycleClassId);
                    throw new IllegalArgumentException(MOTORCYCLE_CLASS_NOT_FOUND_MESSAGE);
                });
    }

    @Override
    public void addMotorcyclesClass(MotorcycleClassDto motorcycleClassDto) {
        MotorcycleClassEntity motorcycleClass = toEntity(motorcycleClassDto);
        motorcycleClassRepository.save(motorcycleClass);
        log.info("Saving new motorcycle class with name -> {}", motorcycleClass.getName());
    }

    @Override
    public void editMotorcycleClass(long motorcycleClassId, MotorcycleClassDto motorcycleClassDto) {
        MotorcycleClassEntity motorcycleClass = getMotorcycleClassEntityById(motorcycleClassId);
        MotorcycleClassEntity updatedMotorcycleClass = toEntity(motorcycleClassDto);
        updatedMotorcycleClass.setId(motorcycleClass.getId());
        motorcycleClassRepository.save(updatedMotorcycleClass);
        log.info("Updating motorcycle class with id -> {}", motorcycleClassId);
    }

    private MotorcycleClassEntity getMotorcycleClassEntityById(long motorcycleClassId) {
        return motorcycleClassRepository.findById(motorcycleClassId)
                .orElseThrow(() -> {
                    log.error("Motorcycle class with id -> {} not found", motorcycleClassId);
                    throw new IllegalArgumentException(MOTORCYCLE_CLASS_NOT_FOUND_MESSAGE);
                });
    }

    @Override
    public void removeMotorcycleClassById(long motorcycleClassId) {
        MotorcycleClassEntity motorcycleClass = getMotorcycleClassEntityById(motorcycleClassId);
        motorcycleClassRepository.delete(motorcycleClass);
        log.info("Removing motorcycle class with id -> {}", motorcycleClassId);
    }
}