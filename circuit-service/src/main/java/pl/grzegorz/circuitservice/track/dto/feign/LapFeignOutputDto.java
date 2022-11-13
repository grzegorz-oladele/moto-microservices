package pl.grzegorz.circuitservice.track.dto.feign;

import lombok.*;

import static lombok.AccessLevel.PRIVATE;

@Getter
@Setter
@AllArgsConstructor(access = PRIVATE)
@Builder
public class LapFeignOutputDto {

    private Long circuitId;
    private String circuitName;
    TrackDetailsFeignOutputDto track;
}
