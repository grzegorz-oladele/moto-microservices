package pl.grzegorz.lapservice.lap.dto.input;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import static lombok.AccessLevel.PRIVATE;

@Getter
@AllArgsConstructor(access = PRIVATE)
@Builder
public class DateDto {

    private String startLapDate;
    private String endLapDate;
}