package pl.grzegorz.lapservice.lap.dto.output;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

import static lombok.AccessLevel.PRIVATE;

@Getter
@AllArgsConstructor(access = PRIVATE)
@Builder
public class BikerOutputDto {

    private Long bikerId;
    private String firstName;
    private String lastName;
    private String pseudonym;
    private String lapTime;
    private LocalDate lapDate;
    private MotorcycleOutputDto motorcycle;
}
