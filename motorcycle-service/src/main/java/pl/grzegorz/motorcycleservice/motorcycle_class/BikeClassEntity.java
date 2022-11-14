package pl.grzegorz.motorcycleservice.motorcycle_class;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import pl.grzegorz.motorcycleservice.motorcycle_class.dto.input.BikeClassDto;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Getter(value = AccessLevel.PACKAGE)
@Setter(value = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@Builder
@Table(name = "motorcycle_classes")
class BikeClassEntity {

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

    static BikeClassEntity toEntity(BikeClassDto bikeClassDto) {
        return BikeClassEntity.builder()
                .name(bikeClassDto.getName())
                .description(bikeClassDto.getDescription())
                .build();
    }
}