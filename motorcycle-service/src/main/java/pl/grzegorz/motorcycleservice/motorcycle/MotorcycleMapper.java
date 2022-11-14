package pl.grzegorz.motorcycleservice.motorcycle;

import lombok.NoArgsConstructor;
import pl.grzegorz.motorcycleservice.motorcycle.dto.output.MotorcycleLapOutputDto;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
class MotorcycleMapper {

    static MotorcycleLapOutputDto toMotorcycleLapOutputDto(MotorcycleEntity motorcycle) {
        return MotorcycleLapOutputDto.builder()
                .motorcycleId(motorcycle.getId())
                .brand(motorcycle.getBrand())
                .model(motorcycle.getModel())
                .capacity(motorcycle.getCapacity())
                .horsePower(motorcycle.getHorsePower())
                .bikerId(motorcycle.getBikerId())
                .build();
    }
}