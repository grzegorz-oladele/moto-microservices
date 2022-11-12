package pl.grzegorz.lapservice.lap;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import static lombok.AccessLevel.PRIVATE;
import static lombok.AccessLevel.PROTECTED;

@Getter(value = PROTECTED)
@Setter(value = PROTECTED)
@AllArgsConstructor(access = PRIVATE)
@Builder
class Biker {

    private Long bikerId;
    private String firstName;
    private String lastName;
}