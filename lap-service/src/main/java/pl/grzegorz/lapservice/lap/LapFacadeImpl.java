package pl.grzegorz.lapservice.lap;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import pl.grzegorz.lapservice.lap.feign.biker.BikerDetails;
import pl.grzegorz.lapservice.lap.feign.motorcycle.MotorcycleDetails;
import pl.grzegorz.lapservice.lap.feign.track.CircuitDetails;
import pl.grzegorz.lapservice.lap.dto.input.DateDto;
import pl.grzegorz.lapservice.lap.dto.input.LapDto;

import java.util.List;

import static pl.grzegorz.lapservice.lap.LapDocument.toLapDocument;

@Service
@RequiredArgsConstructor
@Slf4j
class LapFacadeImpl implements LapFacade {

    private final LapRepository lapRepository;
    private final CircuitFeignClient circuitFeignClient;
    private final BikerFeignClient bikerFeignClient;
    private final MotorcycleFeignClient motorcycleFeignClient;

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
        return circuitFeignClient.getCircuitDetails(trackId, circuitId);
    }

    private BikerDetails getBikerDetails(long bikerId) {
        return bikerFeignClient.getBikerById(bikerId);
    }

    private MotorcycleDetails getMotorcycleById(long motorcycleId) {
        return motorcycleFeignClient.getMotorcycleById(motorcycleId);
    }

    @Override
    public List<LapByCandidateOutputDto> getAllLapsByBiker(long bikerId, int page, int size) {
        return null;
    }

    @Override
    public List<LapByCandidateOutputDto> getAllLapsByBikerAndCircuitAndDateRange(long bikerId, long circuitId, int page,
                                                                                 int size, DateDto dateDto) {
        return null;
    }

    @Override
    public List<LapOutputDto> getAllLapsByCircuitAndDateRangeAndMotorcycleCapacityRange(long circuitId, int page, int size) {
        return null;
    }
}
