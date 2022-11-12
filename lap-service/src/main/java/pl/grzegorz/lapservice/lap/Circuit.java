package pl.grzegorz.lapservice.lap;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import static lombok.AccessLevel.PROTECTED;

@Getter(value = PROTECTED)
@Setter(value = PROTECTED)
@AllArgsConstructor(access = PROTECTED)
@Builder
class Circuit {

    private Long id;
    private String name;
    private Track track;
}