package pl.grzegorz.lapservice.lap;

import lombok.*;

import static lombok.AccessLevel.PROTECTED;

@Getter(value = PROTECTED)
@Setter(value = PROTECTED)
@AllArgsConstructor(access = PROTECTED)
@Builder
class Track {

    private Long id;
    private String name;
    private Double trackLength;
}
