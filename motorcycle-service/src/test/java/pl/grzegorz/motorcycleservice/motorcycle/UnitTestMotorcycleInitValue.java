package pl.grzegorz.motorcycleservice.motorcycle;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import pl.grzegorz.motorcycleservice.bike_class.dto.output.BasicBikeClassOutputDto;
import pl.grzegorz.motorcycleservice.motorcycle.dto.input.MotorcycleDto;
import pl.grzegorz.motorcycleservice.motorcycle.dto.output.MotorcycleOutputDto;

import java.util.List;

import static java.util.Arrays.asList;

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

    static MotorcycleEntity getSecondMotorcycleEntity() {
        return MotorcycleEntity.builder()
                .brand("BMW")
                .model("S1000RR")
                .horsePower(215)
                .vintage(2022)
                .capacity(999)
                .build();
    }

    static MotorcycleDto getMotorcycleDto() {
        return MotorcycleDto.builder()
                .brand("Yamaha")
                .model("YZF R1")
                .capacity(998)
                .horsePower(205)
                .vintage(2021)
                .build();
    }

    static List<MotorcycleOutputDto> getUnitTestingListOfMotorcycleOutputDto() {
        MotorcycleOutputDto firstMotorcycleOutputDto = new MotorcycleOutputDto() {
            @Override
            public Long getId() {
                return 1L;
            }

            @Override
            public String getBrand() {
                return "Ducati";
            }

            @Override
            public String getModel() {
                return "Panigale V2";
            }

            @Override
            public Integer getCapacity() {
                return 965;
            }

            @Override
            public Integer getHorsePower() {
                return 165;
            }

            @Override
            public Integer getVintage() {
                return 2021;
            }

            @Override
            public BasicBikeClassOutputDto getMotorcycleClass() {
                return new BasicBikeClassOutputDto() {
                    @Override
                    public Long getId() {
                        return 1L;
                    }

                    @Override
                    public String getName() {
                        return "Super-sport";
                    }
                };
            }

            @Override
            public String getBikerId() {
                return "1";
            }
        };

        MotorcycleOutputDto secondMotorcycleOutputDto = new MotorcycleOutputDto() {
            @Override
            public Long getId() {
                return 2L;
            }

            @Override
            public String getBrand() {
                return "Triumph";
            }

            @Override
            public String getModel() {
                return "Street triple 765R";
            }

            @Override
            public Integer getCapacity() {
                return 765;
            }

            @Override
            public Integer getHorsePower() {
                return 128;
            }

            @Override
            public Integer getVintage() {
                return 2021;
            }

            @Override
            public BasicBikeClassOutputDto getMotorcycleClass() {
                return new BasicBikeClassOutputDto() {
                    @Override
                    public Long getId() {
                        return 2L;
                    }

                    @Override
                    public String getName() {
                        return "Naked";
                    }
                };
            }

            @Override
            public String getBikerId() {
                return "3";
            }
        };
        return asList(firstMotorcycleOutputDto, secondMotorcycleOutputDto);
    }
}