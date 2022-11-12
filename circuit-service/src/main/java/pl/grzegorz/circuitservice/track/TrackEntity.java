package pl.grzegorz.circuitservice.track;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import pl.grzegorz.circuitservice.circuit.query.CircuitSimpleEntity;
import pl.grzegorz.circuitservice.track.dto.input.TrackDto;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PRIVATE;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Table(name = "tracks")
@Getter(value = PROTECTED)
@Setter(value = PROTECTED)
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor(access = PRIVATE)
@Builder
class TrackEntity {

    @Id
    @GeneratedValue(generator = "sequence_generator")
    @GenericGenerator(
            name = "sequence_generator",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @org.hibernate.annotations.Parameter(name = "sequence_name", value = "track_sequence"),
                    @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
                    @org.hibernate.annotations.Parameter(name = "increment_value", value = "1")
            }
    )
    private Long id;
    private String name;
    private Double trackLength;
    private Boolean isThereFence;
    private Boolean isTherePlaceForAd;
    @ManyToOne
    private CircuitSimpleEntity circuit;

    static TrackEntity toTrackEntity(TrackDto trackDto) {
        return TrackEntity.builder()
                .name(trackDto.getName())
                .trackLength(trackDto.getTrackLength())
                .isThereFence(trackDto.getIsThereFence())
                .isTherePlaceForAd(trackDto.getIsTherePlaceForAd())
                .build();
    }
}