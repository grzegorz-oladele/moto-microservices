package pl.grzegorz.lapservice.lap.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
@Builder
public class LapDto {

    private String lapTime;
    private LocalDate lapDate;
}
