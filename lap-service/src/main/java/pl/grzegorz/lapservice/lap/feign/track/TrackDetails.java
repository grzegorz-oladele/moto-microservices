package pl.grzegorz.lapservice.lap.feign.track;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import static lombok.AccessLevel.PRIVATE;
import static lombok.AccessLevel.PROTECTED;

@Getter
@AllArgsConstructor(access = PRIVATE)
@Builder
public class TrackDetails {

    private Long trackId;
    private String trackName;
    private Double trackLength;
}
