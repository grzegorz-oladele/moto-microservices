package pl.grzegorz.lapservice.motorcycle;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.grzegorz.lapservice.motorcycle.details.MotorcycleDetails;

@Service
@RequiredArgsConstructor
@Slf4j
class MotorcycleFeignFacadeImpl implements MotorcycleFeignFacade {

    private final MotorcycleFeignClient motorcycleFeignClient;

    @Override
    public MotorcycleDetails getMotorcycleById(long motorcycleId) {
        return motorcycleFeignClient.getMotorcycleById(motorcycleId);
    }
}
