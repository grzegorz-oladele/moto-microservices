package pl.grzegorz.lapservice.lap;

import lombok.NoArgsConstructor;
import pl.grzegorz.lapservice.circuit.details.CircuitDetails;
import pl.grzegorz.lapservice.circuit.details.TrackDetails;
import pl.grzegorz.lapservice.lap.dto.output.BikerOutputDto;
import pl.grzegorz.lapservice.lap.dto.output.LapDetailsByBiker;
import pl.grzegorz.lapservice.lap.dto.output.LapDetailsByTrackOutputDto;
import pl.grzegorz.lapservice.lap.dto.output.MotorcycleOutputDto;
import pl.grzegorz.lapservice.motorcycle.details.MotorcycleDetails;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
class LapMapper {

    static List<LapDetailsByBiker> toLapDetailsByBikerList(List<LapDocument> laps) {
        return laps.stream()
                .map(LapMapper::toLapDetailsByBiker)
                .collect(Collectors.toList());
    }

    static LapDetailsByTrackOutputDto toLapDetailsByTrackOutputDto(List<LapDocument> laps, LocalDate startDate,
                                                                   LocalDate endDate) {
        return LapDetailsByTrackOutputDto.builder()
                .circuitName(laps.get(0).getCircuitDetails().getCircuitName())
                .trackName(laps.get(0).getCircuitDetails().getTrack().getTrackName())
                .trackLength(laps.get(0).getCircuitDetails().getTrack().getTrackLength())
                .startDate(startDate)
                .endDate(endDate)
                .bikers(toBikerDtoOutputList(laps))
                .build();
    }

    private static List<BikerOutputDto> toBikerDtoOutputList(List<LapDocument> laps) {
        return laps
                .stream()
                .map(LapMapper::toBikerOutputDto
                )
                .collect(Collectors.toList());
    }

    private static BikerOutputDto toBikerOutputDto(LapDocument lapDocument) {
        return BikerOutputDto.builder()
                .bikerId(lapDocument.getBikerDetails().getId())
                .firstName(lapDocument.getBikerDetails().getFirstName())
                .lastName(lapDocument.getBikerDetails().getLastName())
                .pseudonym(lapDocument.getBikerDetails().getPseudonym())
                .lapTime(lapDocument.getLapTime())
                .lapDate(lapDocument.getLapDate())
                .motorcycle(toMotorcycleOutputDto(lapDocument))
                .build();
    }

    private static MotorcycleOutputDto toMotorcycleOutputDto(LapDocument lapDocument) {
        return MotorcycleOutputDto.builder()
                .motorcycleId(lapDocument.getBikerDetails().getId())
                .brand(lapDocument.getMotorcycleDetails().getBrand())
                .model(lapDocument.getMotorcycleDetails().getModel())
                .capacity(lapDocument.getMotorcycleDetails().getCapacity())
                .horsePower(lapDocument.getMotorcycleDetails().getHorsePower())
                .build();
    }

    private static LapDetailsByBiker toLapDetailsByBiker(LapDocument lapDocument) {
        return LapDetailsByBiker.builder()
                .id(lapDocument.getId())
                .lapTime(lapDocument.getLapTime())
                .lapDate(lapDocument.getLapDate())
                .motorcycleDetails(toMotorcycleDetails(lapDocument))
                .circuitDetails(toCircuitDetails(lapDocument))
                .build();
    }

    private static MotorcycleDetails toMotorcycleDetails(LapDocument lapDocument) {
        return MotorcycleDetails.builder()
                .motorcycleId(lapDocument.getBikerDetails().getId())
                .brand(lapDocument.getMotorcycleDetails().getBrand())
                .model(lapDocument.getMotorcycleDetails().getModel())
                .capacity(lapDocument.getMotorcycleDetails().getCapacity())
                .horsePower(lapDocument.getMotorcycleDetails().getHorsePower())
                .bikerId(lapDocument.getBikerDetails().getId())
                .build();
    }

    private static CircuitDetails toCircuitDetails(LapDocument lapDocument) {
        return CircuitDetails.builder()
                .circuitId(lapDocument.getCircuitDetails().getCircuitId())
                .circuitName(lapDocument.getCircuitDetails().getCircuitName())
                .track(toTrackDetails(lapDocument))
                .build();
    }

    private static TrackDetails toTrackDetails(LapDocument lapDocument) {
        return TrackDetails.builder()
                .trackId(lapDocument.getCircuitDetails().getTrack().getTrackId())
                .trackName(lapDocument.getCircuitDetails().getTrack().getTrackName())
                .trackLength(lapDocument.getCircuitDetails().getTrack().getTrackLength())
                .build();
    }
}