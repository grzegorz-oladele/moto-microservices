package pl.grzegorz.motorcycleservice.motorcycle;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import pl.grzegorz.motorcycleservice.motorcycle_class.query.BikeClassSimpleEntity;
import pl.grzegorz.motorcycleservice.motorcycle.dto.input.MotorcycleDto;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "motorcycles")
@NoArgsConstructor
@AllArgsConstructor
@Builder
class MotorcycleEntity {

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
    private String brand;
    private String model;
    private int capacity;
    private int horsePower;
    private int vintage;
    @ManyToOne
    @JoinColumn(name = "class_id")
    private BikeClassSimpleEntity motorcycleClass;
    private Long bikerId;

    static MotorcycleEntity toEntity(MotorcycleDto motorcycleDto) {
        return MotorcycleEntity.builder()
                .brand(motorcycleDto.getBrand())
                .model(motorcycleDto.getModel())
                .capacity(motorcycleDto.getCapacity())
                .vintage(motorcycleDto.getVintage())
                .horsePower(motorcycleDto.getHorsePower())
                .build();
    }
}
