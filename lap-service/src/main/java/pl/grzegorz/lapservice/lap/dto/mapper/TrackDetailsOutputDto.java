package pl.grzegorz.lapservice.lap.dto.mapper;

import lombok.*;

import static lombok.AccessLevel.PRIVATE;
import static lombok.AccessLevel.PROTECTED;

@Getter(value = PROTECTED)
@Setter(value = PROTECTED)
@AllArgsConstructor(access = PRIVATE)
@Builder
class TrackDetailsOutputDto {

    private Long trackId;
    private String name;
    private String trackLength;
}