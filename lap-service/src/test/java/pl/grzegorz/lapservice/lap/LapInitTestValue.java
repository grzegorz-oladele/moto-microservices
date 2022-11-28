package pl.grzegorz.lapservice.lap;

import lombok.NoArgsConstructor;
import pl.grzegorz.lapservice.biker.details.BikerDetails;
import pl.grzegorz.lapservice.circuit.details.CircuitDetails;
import pl.grzegorz.lapservice.circuit.details.TrackDetails;
import pl.grzegorz.lapservice.lap.dto.input.DateDto;
import pl.grzegorz.lapservice.lap.dto.input.DateLapDto;
import pl.grzegorz.lapservice.lap.dto.input.LapDto;
import pl.grzegorz.lapservice.motorcycle.details.MotorcycleDetails;

import static java.time.LocalDate.parse;
import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
class LapInitTestValue {

    static LapDocument getLapDocument() {
        return LapDocument.builder()
                .lapTime("1:22:345")
                .lapDate(parse("2022-03-21"))
                .bikerDetails(getBikerDetails())
                .motorcycleDetails(getMotorcycleDetails())
                .circuitDetails(getCircuitDetails())
                .build();
    }

    private static BikerDetails getBikerDetails() {
        return BikerDetails.builder()
                .id(1L)
                .firstName("Tomasz")
                .lastName("Tomaszewski")
                .username("tomasz_123")
                .build();
    }

    private static MotorcycleDetails getMotorcycleDetails() {
        return MotorcycleDetails.builder()
                .motorcycleId(3L)
                .brand("BMW")
                .model("S1000RR")
                .capacity(999)
                .horsePower(215)
                .bikerId(getBikerDetails().getId())
                .build();
    }

    private static CircuitDetails getCircuitDetails() {
        return CircuitDetails.builder()
                .circuitId(5L)
                .circuitName("Masaryk Circuit")
                .track(getTrackDetails())
                .build();
    }

    private static TrackDetails getTrackDetails() {
        return TrackDetails.builder()
                .trackId(22L)
                .trackName("Main track")
                .trackLength(7652.2)
                .build();
    }

    public static DateDto getDateDto() {
        return DateDto.builder()
                .startLapDate("2022-03-20")
                .endLapDate("2022-03-22")
                .build();
    }

    public static DateLapDto getDateLapDto() {
        return DateLapDto.builder()
                .startDate("2022-03-20")
                .endDate("2022-03-22")
                .startCapacity(500)
                .endCapacity(1000)
                .build();
    }

    public static LapDto getLapDto() {
        return LapDto.builder()
                .lapDate("2022-04-13")
                .lapTime("2:33:492")
                .build();
    }
}