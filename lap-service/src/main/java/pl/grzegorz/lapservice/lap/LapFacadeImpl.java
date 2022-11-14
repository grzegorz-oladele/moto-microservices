package pl.grzegorz.lapservice.lap;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.grzegorz.lapservice.biker.BikerFeignFacade;
import pl.grzegorz.lapservice.circuit.CircuitFeignFacade;
import pl.grzegorz.lapservice.lap.dto.input.DateDto;
import pl.grzegorz.lapservice.lap.dto.input.LapDto;
import pl.grzegorz.lapservice.biker.details.BikerDetails;
import pl.grzegorz.lapservice.motorcycle.details.MotorcycleDetails;
import pl.grzegorz.lapservice.circuit.details.CircuitDetails;
import pl.grzegorz.lapservice.motorcycle.MotorcycleFeignFacade;

import java.util.List;

import static pl.grzegorz.lapservice.lap.LapDocument.toLapDocument;

@Service
@RequiredArgsConstructor
@Slf4j
class LapFacadeImpl implements LapFacade {

    private final LapRepository lapRepository;
    private final MotorcycleFeignFacade motorcycleFeignFacade;
    private final CircuitFeignFacade circuitFeignFacade;
    private final BikerFeignFacade bikerFeignFacade;

    @Override
    public void addNewLap(long circuitId, long trackId, long bikerId, long motorcycleId, LapDto lapDto) {
        LapDocument lap = createLap(circuitId, trackId, bikerId, motorcycleId, lapDto);
        log.info(lap.toString());
        lapRepository.save(lap);
    }

    private LapDocument createLap(long circuitId, long trackId, long bikerId, long motorcycleId, LapDto lapDto) {
        LapDocument newLap = toLapDocument(lapDto);
        newLap.setCircuitDetails(getCircuitDetails(trackId, circuitId));
        newLap.setBikerDetails(getBikerDetails(bikerId));
        newLap.setMotorcycleDetails(getMotorcycleById(motorcycleId));
        return newLap;
    }

    private CircuitDetails getCircuitDetails(long trackId, long circuitId) {
        return circuitFeignFacade.getCircuitDetails(trackId, circuitId);
    }

    private BikerDetails getBikerDetails(long bikerId) {
        return bikerFeignFacade.getBikerById(bikerId);
    }

    private MotorcycleDetails getMotorcycleById(long motorcycleId) {
        return motorcycleFeignFacade.getMotorcycleById(motorcycleId);
    }

//    @Override
//    public List<LapByCandidateOutputDto> getAllLapsByBiker(long bikerId, int page, int size) {
//        return null;
//    }
//
//    @Override
//    public List<LapByCandidateOutputDto> getAllLapsByBikerAndCircuitAndDateRange(long bikerId, long circuitId, int page,
//                                                                                 int size, DateDto dateDto) {
//        return null;
//    }
//
//    @Override
//    public List<LapOutputDto> getAllLapsByCircuitAndDateRangeAndMotorcycleCapacityRange(long circuitId, int page, int size) {
//        return null;
//    }
}