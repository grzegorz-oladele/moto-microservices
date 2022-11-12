package pl.grzegorz.lapservice.lap;

import pl.grzegorz.lapservice.lap.dto.input.DateDto;
import pl.grzegorz.lapservice.lap.dto.output.LapByCandidateOutputDto;
import pl.grzegorz.lapservice.lap.dto.output.LapOutputDto;

import java.util.List;

public interface LapFacade {

    void addLap(long bikerId, long motorcycleId, long circuitId, long trackId);

    List<LapByCandidateOutputDto> getAllLapsByBiker(long bikerId, int page, int size);

    List<LapByCandidateOutputDto> getAllLapsByCandidateAndDateRange(long bikerId, long circuitId, int page, int size,
                                                                    DateDto dateDto);

    List<LapOutputDto> getAllLapsByCircuitAndDateRangeAndMotorcycleCapacityRange(long circuitId, int page, int size);
}