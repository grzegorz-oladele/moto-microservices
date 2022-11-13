package pl.grzegorz.circuitservice.track.dto.feign;

import lombok.*;

import static lombok.AccessLevel.PRIVATE;

@Getter
@Setter
@AllArgsConstructor(access = PRIVATE)
@Builder
public class TrackDetailsFeignOutputDto {

    private Long trackId;
    private String trackName;
    private Double trackLength;
}
