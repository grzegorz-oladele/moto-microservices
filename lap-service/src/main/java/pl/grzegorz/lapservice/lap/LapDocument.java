package pl.grzegorz.lapservice.lap;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import pl.grzegorz.lapservice.lap.dto.LapDto;

import java.time.LocalDate;

import static lombok.AccessLevel.PROTECTED;

@Document("laps")
@Getter(value = PROTECTED)
@Setter(value = PROTECTED)
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor(access = PROTECTED)
@Builder
class LapDocument {

    @Id
    private String id;
    private String lapTime;
    private LocalDate lapDate;
    private Biker biker;
    private Motorcycle motorcycle;
    private Circuit circuit;

    static LapDocument toLapDocument(LapDto lapDto) {
        return LapDocument.builder()
                .lapTime(lapDto.getLapTime())
                .lapDate(lapDto.getLapDate())
                .build();
    }
}