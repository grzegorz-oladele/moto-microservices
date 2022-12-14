package pl.grzegorz.motorcycleservice.motorcycle_class;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import pl.grzegorz.motorcycleservice.motorcycle_class.dto.input.BikeClassDto;
import pl.grzegorz.motorcycleservice.motorcycle_class.dto.output.BikeClassOutputDto;
import pl.grzegorz.motorcycleservice.motorcycle_class.query.BikeClassSimpleEntity;

import java.util.List;

import static java.util.Arrays.asList;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BikeClassTestInitValue {

    static List<BikeClassOutputDto> getUnitTestingListOfBikeClassOutputDto() {
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

    public static BikeClassDto getUnitTestingMotorcycleClassDto() {
        return BikeClassDto.builder()
                .name("Cafe Racer")
                .description("Small-capacity motorcycles for getting around town fast")
                .build();
    }

    public static BikeClassSimpleEntity getUnitTestBikeClassSimpleEntity() {
        return BikeClassSimpleEntity.builder()
                .id(1L)
                .name("Supersport")
                .build();
    }
}
