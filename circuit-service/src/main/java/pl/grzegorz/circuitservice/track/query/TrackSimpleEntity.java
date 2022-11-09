package pl.grzegorz.circuitservice.track.query;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import pl.grzegorz.circuitservice.circuit.query.CircuitSimpleEntity;

import javax.persistence.*;

import static lombok.AccessLevel.PRIVATE;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor(access = PRIVATE)
@Builder
@Table(name = "tracks")
public class TrackSimpleEntity {

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
    private Double circuitLength;
    @ManyToOne
    private CircuitSimpleEntity circuit;
}
