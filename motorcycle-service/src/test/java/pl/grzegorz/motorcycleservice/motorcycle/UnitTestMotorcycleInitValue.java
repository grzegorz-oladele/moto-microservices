package pl.grzegorz.motorcycleservice.motorcycle;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
class UnitTestMotorcycleInitValue {

    static MotorcycleEntity getMotorcycleEntity() {
        return MotorcycleEntity.builder()
                .brand("Ducati")
                .model("Panigale V4S")
                .horsePower(221)
                .vintage(2022)
                .capacity(1098)
                .build();
    }
}
