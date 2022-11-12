package pl.grzegorz.lapservice.lap.dto.input;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

import static lombok.AccessLevel.PRIVATE;

@Getter
@AllArgsConstructor(access = PRIVATE)
@Builder
public class DateDto {

    private LocalDate startDate;
    private LocalDate endDate;
}
