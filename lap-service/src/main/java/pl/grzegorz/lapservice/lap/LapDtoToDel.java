package pl.grzegorz.lapservice.lap;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
class LapDtoToDel {

    private String lapTime;
    private LocalDate lapDate;
}
