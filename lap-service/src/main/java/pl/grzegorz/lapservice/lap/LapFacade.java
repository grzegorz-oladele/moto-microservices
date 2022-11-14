package pl.grzegorz.lapservice.lap;

import pl.grzegorz.lapservice.lap.dto.input.DateDto;
import pl.grzegorz.lapservice.lap.dto.input.LapDto;
import pl.grzegorz.lapservice.lap.dto.output.LapDetailsByBiker;

import java.util.List;

public interface LapFacade {

    List<LapDetailsByBiker> getAllLapsByBiker(long bikerId, int page, int size);

    List<LapDetailsByBiker> getAllLapsByBikerAndCircuitAndDateRange(long bikerId, long circuitId, int page, int size,
                                                                    DateDto dateDto);

    void addNewLap(long trackId, long bikerId, long motorcycleId, LapDto lapDto);
//
//    List<LapOutputDto> getAllLapsByCircuitAndDateRangeAndMotorcycleCapacityRange(long circuitId, int page, int size);
}