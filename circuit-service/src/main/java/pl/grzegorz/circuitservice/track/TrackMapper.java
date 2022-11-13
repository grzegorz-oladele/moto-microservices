package pl.grzegorz.circuitservice.track;

import lombok.NoArgsConstructor;
import pl.grzegorz.circuitservice.track.dto.feign.LapFeignOutputDto;
import pl.grzegorz.circuitservice.track.dto.feign.TrackDetailsFeignOutputDto;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
class TrackMapper {

    static LapFeignOutputDto toLapFeignOutputDto(TrackEntity track) {
        return LapFeignOutputDto.builder()
                .circuitId(track.getCircuit().getId())
                .circuitName(track.getCircuit().getName())
                .track(toTrackDetailsOutputDto(track))
                .build();
    }

    private static TrackDetailsFeignOutputDto toTrackDetailsOutputDto(TrackEntity track) {
        return TrackDetailsFeignOutputDto.builder()
                .trackId(track.getId())
                .trackName(track.getName())
                .trackLength(track.getTrackLength())
                .build();
    }
}