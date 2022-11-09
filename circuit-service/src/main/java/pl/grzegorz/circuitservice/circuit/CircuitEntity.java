package pl.grzegorz.circuitservice.circuit;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import pl.grzegorz.circuitservice.circuit.dto.input.CircuitDto;
import pl.grzegorz.circuitservice.track.query.TrackSimpleEntity;

import javax.persistence.*;
import java.util.List;

import static lombok.AccessLevel.PROTECTED;

@Entity
@Table(name = "circuits")
@Getter(value = PROTECTED)
@Setter(value = PROTECTED)
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor(access = PROTECTED)
@Builder
class CircuitEntity {

    @Id
    @GeneratedValue(generator = "sequence_generator")
    @GenericGenerator(
            name = "sequence_generator",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @org.hibernate.annotations.Parameter(name = "sequence_name", value = "motorcycle_class_sequence"),
                    @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
                    @org.hibernate.annotations.Parameter(name = "increment_value", value = "1")
            }
    )
    private Long id;
    private String name;
    private String description;
    private String city;
    private String postalCode;
    private String street;
    private String streetNumber;
    private String email;
    private String phoneNumber;
    private CircuitType type;
    @OneToMany(mappedBy = "circuit")
    private List<TrackSimpleEntity> listOfTracks;

    static CircuitEntity toMotorcycleTrackEntity(CircuitDto circuitDto) {
        return CircuitEntity.builder()
                .name(circuitDto.getName())
                .description(circuitDto.getDescription())
                .city(circuitDto.getCity())
                .postalCode(circuitDto.getPostalCode())
                .street(circuitDto.getStreet())
                .streetNumber(circuitDto.getStreetNumber())
                .email(circuitDto.getEmail())
                .phoneNumber(circuitDto.getPhoneNumber())
                .type(CircuitType.valueOf(circuitDto.getType()))
                .build();
    }
}