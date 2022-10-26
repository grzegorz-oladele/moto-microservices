package pl.grzegorz.motorcycleservice.motorcycle;

import lombok.*;
import pl.grzegorz.motorcycleservice.motorcycle_class.query.MotorcycleClassSimpleEntity;

import javax.persistence.*;

@Entity
@Getter
@Setter
//@Table(name = "motorcycles")
@NoArgsConstructor
@AllArgsConstructor
@Builder
class MotorcycleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String brand;
    private String model;
    private int capacity;
    private int horsePower;
    private int vintage;
    @ManyToOne
    @JoinColumn(name = "class_id")
    private MotorcycleClassSimpleEntity motorcycleClass;
    private String bikerId;

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
