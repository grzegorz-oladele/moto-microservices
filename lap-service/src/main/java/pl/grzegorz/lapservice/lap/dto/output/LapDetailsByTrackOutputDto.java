package pl.grzegorz.lapservice.lap.dto.output;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@Getter
@AllArgsConstructor(access = PRIVATE)
@Builder
public class LapDetailsByTrackOutputDto {

    private String circuitName;
    private String trackName;
    private Double trackLength;
    private LocalDate startDate;
    private LocalDate endDate;
    private List<BikerOutputDto> bikers;
}