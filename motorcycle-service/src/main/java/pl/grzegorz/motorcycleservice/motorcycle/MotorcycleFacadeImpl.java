package pl.grzegorz.motorcycleservice.motorcycle;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static pl.grzegorz.motorcycleservice.motorcycle.MotorcycleEntity.*;

@Service
@RequiredArgsConstructor
@Slf4j
class MotorcycleFacadeImpl implements MotorcycleFacade {

    private final MotorcycleRepository motorcycleRepository;
    private final MotorcycleQueryRepository motorcycleQueryRepository;

    @Override
    public MotorcycleOutputDto getMotorcycleById(long motorcycleId) {
        return motorcycleQueryRepository.findById(motorcycleId)
                .orElseThrow(() -> {
                    log.error("Motorcycle wit id -> {} not found", motorcycleId);
                    throw new IllegalArgumentException("Motorcycle not found");
                });
    }

    @Override
    public void AddMotorcycle(MotorcycleDto motorcycleDto) {
        MotorcycleEntity motorcycle = toEntity(motorcycleDto);
        motorcycleRepository.save(motorcycle);
    }
}
