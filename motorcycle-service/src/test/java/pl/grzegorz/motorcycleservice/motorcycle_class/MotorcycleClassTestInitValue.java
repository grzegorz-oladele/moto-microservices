package pl.grzegorz.motorcycleservice.motorcycle_class;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import pl.grzegorz.motorcycleservice.motorcycle_class.dto.input.MotorcycleClassDto;
import pl.grzegorz.motorcycleservice.motorcycle_class.dto.output.MotorcycleClassOutputDto;

import java.util.List;

import static java.util.Arrays.asList;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
class MotorcycleClassTestInitValue {

    static List<MotorcycleClassOutputDto> getUnitTestingListOfMotorcycleClassOutputDto() {
        MotorcycleClassOutputDto firstMotorcycleClassOutputDto = new MotorcycleClassOutputDto() {
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

        MotorcycleClassOutputDto secondMotorcycleClassOutputDto = new MotorcycleClassOutputDto() {
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
        return asList(firstMotorcycleClassOutputDto, secondMotorcycleClassOutputDto);
    }

    static MotorcycleClassEntity getUnitTestingMotorcycleClassEntity() {
        return MotorcycleClassEntity.builder()
                .name("Power Cruiser")
                .description("Powerful and heavy motorcycle")
                .build();
    }

    static MotorcycleClassDto getUnitTestingMotorcycleClassDto() {
        return MotorcycleClassDto.builder()
                .name("Cafe Racer")
                .description("Small-capacity motorcycles for getting around town fast")
                .build();
    }
}
