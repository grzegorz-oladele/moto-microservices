package pl.grzegorz.lapservice.lap.dto.input;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import static lombok.AccessLevel.PRIVATE;

@Getter
@AllArgsConstructor(access = PRIVATE)
@Builder
public class DateLapDto {

    private String startDate;
    private String endDate;
    private int startCapacity;
    private int endCapacity;
}
