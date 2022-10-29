package pl.grzegorz.motorcycleservice.bike_class;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import pl.grzegorz.motorcycleservice.bike_class.dto.input.BikeClassDto;
import pl.grzegorz.motorcycleservice.bike_class.dto.output.BikeClassOutputDto;

import java.util.List;

import static java.util.Arrays.asList;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
class MotorcycleClassTestInitValue {

    static List<BikeClassOutputDto> getUnitTestingListOfMotorcycleClassOutputDto() {
        BikeClassOutputDto firstBikeClassOutputDto = new BikeClassOutputDto() {
            @Override
            public Long getId() {
                return 1L;
            }

            @Override
            public String getName() {
                return "Super Sport";
            }

            @Override
            public String getDescription() {
                return "Sports motorcycles with large engine capacity";
            }
        };

        BikeClassOutputDto secondBikeClassOutputDto = new BikeClassOutputDto() {
            @Override
            public Long getId() {
                return 2L;
            }

            @Override
            public String getName() {
                return "Naked";
            }

            @Override
            public String getDescription() {
                return "City motorcycles with sporty flair";
            }
        };
        return asList(firstBikeClassOutputDto, secondBikeClassOutputDto);
    }

    static BikeClassEntity getFirstMotorcycleClassEntity() {
        return BikeClassEntity.builder()
                .name("Power Cruiser")
                .description("Powerful and heavy motorcycle")
                .build();
    }

    static BikeClassEntity getSecondMotorcycleClassEntity() {
        return BikeClassEntity.builder()
                .name("Supersport")
                .description("Optimized for speed, accelerating and braking")
                .build();
    }

    static BikeClassDto getUnitTestingMotorcycleClassDto() {
        return BikeClassDto.builder()
                .name("Cafe Racer")
                .description("Small-capacity motorcycles for getting around town fast")
                .build();
    }
}
