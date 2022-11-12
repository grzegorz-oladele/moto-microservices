package pl.grzegorz.lapservice.lap.dto.mapper;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import static lombok.AccessLevel.PRIVATE;

@Getter
@Setter
@AllArgsConstructor(access = PRIVATE)
@Builder
class LapDetailsOutputDto {

    private TrackDetailsOutputDto trackDetails;
    private BikerDetailsOutputDto biker;
    private MotorcycleDetailsOutputDto motorcycle;
}
