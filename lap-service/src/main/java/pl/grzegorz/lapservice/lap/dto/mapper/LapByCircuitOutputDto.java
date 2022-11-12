package pl.grzegorz.lapservice.lap.dto.mapper;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@Getter
@Setter
@AllArgsConstructor(access = PRIVATE)
@Builder
public class LapByCircuitOutputDto {

    private Long circuitId;
    private String circuitName;
    private List<LapDetailsOutputDto> lapDetails;
}