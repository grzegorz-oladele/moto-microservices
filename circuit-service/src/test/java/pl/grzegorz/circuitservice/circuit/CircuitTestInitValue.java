package pl.grzegorz.circuitservice.circuit;

import lombok.NoArgsConstructor;
import pl.grzegorz.circuitservice.circuit.dto.input.CircuitDto;
import pl.grzegorz.circuitservice.circuit.dto.output.CircuitOutputDto;
import pl.grzegorz.circuitservice.circuit.dto.output.CircuitSimpleOutputDto;
import pl.grzegorz.circuitservice.circuit.query.CircuitSimpleEntity;
import pl.grzegorz.circuitservice.track.dto.output.TrackSimpleOutputDto;
import pl.grzegorz.circuitservice.track.query.TrackSimpleEntity;

import java.util.List;

import static com.google.common.collect.ImmutableList.of;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static lombok.AccessLevel.PRIVATE;
import static pl.grzegorz.circuitservice.track.TrackTestInitValue.getTrackSimpleEntity;

@NoArgsConstructor(access = PRIVATE)
public
class CircuitTestInitValue {

    private static final TrackSimpleEntity trackSimpleEntity = getTrackSimpleEntity();

    static List<CircuitOutputDto> getListOfCircuitOutputDto() {
        CircuitOutputDto firstCircuit = new CircuitOutputDto() {
            @Override
            public Long getId() {
                return 1L;
            }

            @Override
            public String getName() {
                return "Tor Poznań";
            }

            @Override
            public String getDescription() {
                return "Największy tor w Polsce";
            }

            @Override
            public String getCity() {
                return "Poznań";
            }

            @Override
            public String getPostalCode() {
                return "00-000";
            }

            @Override
            public String getStreet() {
                return "Poznańska";
            }

            @Override
            public String getStreetNumber() {
                return "3";
            }

            @Override
            public String getEmail() {
                return "tor@poznan.pl";
            }

            @Override
            public String getPhoneNumber() {
                return "123-456-789";
            }

            @Override
            public List<TrackSimpleOutputDto> getListOfTracks() {
                return emptyList();
            }
        };

        CircuitOutputDto secondCircuit = new CircuitOutputDto() {
            @Override
            public Long getId() {
                return 2L;
            }

            @Override
            public String getName() {
                return "Tor Jastrząb";
            }

            @Override
            public String getDescription() {
                return "Tor w okolicy Radomia";
            }

            @Override
            public String getCity() {
                return "Jastrząb";
            }

            @Override
            public String getPostalCode() {
                return "26-502";
            }

            @Override
            public String getStreet() {
                return "Czerwienica";
            }

            @Override
            public String getStreetNumber() {
                return "25";
            }

            @Override
            public String getEmail() {
                return "tor@jastrzab.pl";
            }

            @Override
            public String getPhoneNumber() {
                return "987-654-321";
            }

            @Override
            public List<TrackSimpleOutputDto> getListOfTracks() {
                return emptyList();
            }
        };
        return asList(firstCircuit, secondCircuit);
    }

    static CircuitEntity getCircuitEntity() {
        return CircuitEntity.builder()
                .id(1L)
                .name("Tor Poznan")
                .description("Najwiekszy tor w Polsce")
                .city("Poznan")
                .postalCode("00-000")
                .street("Poznanska")
                .streetNumber("3")
                .email("tor@poznan.pl")
                .phoneNumber("123-456-789")
                .listOfTracks(emptyList())
                .build();
    }

    static CircuitEntity getSecondCircuitEntity() {
        return CircuitEntity.builder()
                .name("Tor Jastrzab")
                .description("Tor w okolicy Radomia")
                .city("Jastrzab")
                .postalCode("26-502")
                .street("Czerwienica")
                .streetNumber("25")
                .email("tor@jastrzab.pl")
                .phoneNumber("987-654-321")
                .build();
    }

    public static CircuitSimpleEntity getCircuitSimpleEntity() {
        return CircuitSimpleEntity.builder()
                .id(1L)
                .name("Tor Poznań")
//                .listOfCircuits(of(trackSimpleEntity))
                .build();
    }

    static CircuitDto getCircuitDto() {
        return CircuitDto.builder()
                .name("Tor Poznań")
                .description("Największy tor w Polsce")
                .city("Poznań")
                .postalCode("00-000")
                .street("Poznańska")
                .streetNumber("3")
                .email("tor@poznan.pl")
                .phoneNumber("123-456-789")
                .type("SPEED")
                .build();
    }

    static CircuitDto getSecondCircuitDto() {
        return CircuitDto.builder()
                .name("Silesia Ring")
                .description("Tor na slasku")
                .city("Kamien Slaski")
                .postalCode("47-325")
                .street("Lotnicza")
                .streetNumber("5-7")
                .email("silesia@ring.pl")
                .phoneNumber("691-017-555")
                .type("SPEED")
                .build();
    }

    public static CircuitSimpleOutputDto getCircuitSimpleOutputDto() {
        return new CircuitSimpleOutputDto() {
            @Override
            public Long getId() {
                return 1L;
            }

            @Override
            public String getName() {
                return "Tor Poznań";
            }
        };
    }

    public static CircuitSimpleOutputDto getSecondCircuitSimpleOutputDto() {
        return new CircuitSimpleOutputDto() {
            @Override
            public Long getId() {
                return 2L;
            }

            @Override
            public String getName() {
                return "Tor Jastrząb";
            }
        };
    }
}
