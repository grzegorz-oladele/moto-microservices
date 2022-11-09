package pl.grzegorz.circuitservice.circuit.query;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import pl.grzegorz.circuitservice.track.query.TrackSimpleEntity;

import javax.persistence.*;

import java.util.List;

import static lombok.AccessLevel.PROTECTED;

@Entity
@Table(name = "circuits")
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
@Builder
public class CircuitSimpleEntity {

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
    @OneToMany(mappedBy = "circuit")
    private List<TrackSimpleEntity> listOfCircuits;
}