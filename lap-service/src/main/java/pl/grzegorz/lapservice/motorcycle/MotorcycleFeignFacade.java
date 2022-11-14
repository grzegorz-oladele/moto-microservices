package pl.grzegorz.lapservice.motorcycle;

import pl.grzegorz.lapservice.motorcycle.details.MotorcycleDetails;

public interface MotorcycleFeignFacade {

    MotorcycleDetails getMotorcycleById(long motorcycleId);

}
