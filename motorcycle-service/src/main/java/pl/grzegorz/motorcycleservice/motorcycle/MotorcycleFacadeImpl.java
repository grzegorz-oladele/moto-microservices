package pl.grzegorz.motorcycleservice.motorcycle;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
class MotorcycleFacadeImpl implements MotorcycleFacade {

    private final MotorcycleRepository motorcycleRepository;
    private final MotorcycleQueryRepository motorcycleQueryRepository;
}
