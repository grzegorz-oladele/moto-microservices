package pl.grzegorz.lapservice.lap.dto.input;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
@Builder
public class LapDto {

    private String lapTime;
    private String lapDate;
}
