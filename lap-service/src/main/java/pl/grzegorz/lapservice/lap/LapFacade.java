package pl.grzegorz.lapservice.lap;

import pl.grzegorz.lapservice.lap.dto.input.DateDto;
import pl.grzegorz.lapservice.lap.dto.input.LapDto;

import java.util.List;

public interface LapFacade {

    void addNewLap(long circuitId, long trackId, long bikerId, long motorcycleId, LapDto lapDto);

    List<LapByCandidateOutputDto> getAllLapsByBiker(long bikerId, int page, int size);

    List<LapByCandidateOutputDto> getAllLapsByBikerAndCircuitAndDateRange(long bikerId, long circuitId, int page, int size,
                                                                    DateDto dateDto);

    List<LapOutputDto> getAllLapsByCircuitAndDateRangeAndMotorcycleCapacityRange(long circuitId, int page, int size);
}