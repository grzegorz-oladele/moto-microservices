package pl.grzegorz.lapservice.lap.dto.mapper;

import lombok.*;

import static lombok.AccessLevel.PRIVATE;
import static lombok.AccessLevel.PROTECTED;

@Getter(value = PROTECTED)
@Setter(value = PROTECTED)
@AllArgsConstructor(access = PRIVATE)
@Builder
class BikerDetailsOutputDto {

    private Long bikerId;
    private String firstName;
    private String lastName;
}