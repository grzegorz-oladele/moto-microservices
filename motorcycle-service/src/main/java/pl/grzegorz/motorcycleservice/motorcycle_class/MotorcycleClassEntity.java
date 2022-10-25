package pl.grzegorz.motorcycleservice.motorcycle_class;

import lombok.*;
import pl.grzegorz.motorcycleservice.motorcycle_class.dto.input.MotorcycleClassDto;

import javax.persistence.*;

@Entity
@Getter(value = AccessLevel.PACKAGE)
@Setter(value = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@Builder
@Table(name = "motorcycle_classes")
class MotorcycleClassEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;

    static MotorcycleClassEntity toEntity(MotorcycleClassDto motorcycleClassDto) {
        return MotorcycleClassEntity.builder()
                .name(motorcycleClassDto.getName())
                .description(motorcycleClassDto.getDescription())
                .build();
    }
}