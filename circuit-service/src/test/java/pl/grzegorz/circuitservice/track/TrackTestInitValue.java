package pl.grzegorz.circuitservice.track;

import lombok.NoArgsConstructor;
import pl.grzegorz.circuitservice.circuit.dto.output.CircuitSimpleOutputDto;
import pl.grzegorz.circuitservice.circuit.query.CircuitSimpleEntity;
import pl.grzegorz.circuitservice.track.dto.input.TrackDto;
import pl.grzegorz.circuitservice.track.dto.output.TrackOutputDto;
import pl.grzegorz.circuitservice.track.dto.output.TrackSimpleOutputDto;

import java.util.List;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static java.util.Arrays.asList;
import static lombok.AccessLevel.PRIVATE;
import static pl.grzegorz.circuitservice.circuit.CircuitTestInitValue.*;

@NoArgsConstructor(access = PRIVATE)
public class TrackTestInitValue {

    private static final CircuitSimpleEntity circuitSimpleEntity = getCircuitSimpleEntity();

    static List<TrackOutputDto> getTrackOutputDtoList() {
        TrackOutputDto firstTrack = new TrackOutputDto() {
            @Override
            public Long getId() {
                return 1L;
            }

            @Override
            public String getName() {
                return "Tor nr 1";
            }

            @Override
            public Double getTrackLength() {
                return 1923D;
            }

            @Override
            public Boolean getIsThereFence() {
                return TRUE;
            }

            @Override
            public Boolean getIsTherePlaceForAd() {
                return FALSE;
            }

            @Override
            public CircuitSimpleOutputDto getCircuit() {
                return getCircuitSimpleOutputDto();
            }
        };

        TrackOutputDto secondTrack = new TrackOutputDto() {
            @Override
            public Long getId() {
                return 2L;
            }

            @Override
            public String getName() {
                return "Tor nr 2";
            }

            @Override
            public Double getTrackLength() {
                return 2937D;
            }

            @Override
            public Boolean getIsThereFence() {
                    return FALSE;
            }

            @Override
            public Boolean getIsTherePlaceForAd() {
                return TRUE;
            }

            @Override
            public CircuitSimpleOutputDto getCircuit() {
                return getSecondCircuitSimpleOutputDto();
            }
        };
        return asList(firstTrack, secondTrack);
    }

    static TrackSimpleOutputDto getTrackSimpleOutputDto() {
        return new TrackSimpleOutputDto() {
            @Override
            public Long getId() {
                return 1L;
            }

            @Override
            public String getName() {
                return "Tor nr 1";
            }

            @Override
            public Double getTrackLength() {
                return 1923D;
            }
        };
    }

    static TrackEntity getTrackEntity() {
        return TrackEntity.builder()
                .id(1L)
                .name("Tor nr 1")
                .trackLength(2937D)
                .isThereFence(FALSE)
                .isTherePlaceForAd(TRUE)
                .circuit(circuitSimpleEntity)
                .build();
    }

    static TrackEntity getSecondTrackEntity() {
        return TrackEntity.builder()
                .id(2L)
                .name("Tor nr 2")
                .trackLength(1827D)
                .isThereFence(TRUE)
                .isTherePlaceForAd(FALSE)
                .circuit(circuitSimpleEntity)
                .build();
    }

    static TrackDto getTrackDto() {
        return TrackDto.builder()
                .name("Tor nr 4")
                .trackLength(3472D)
                .isThereFence(TRUE)
                .isTherePlaceForAd(TRUE)
                .build();
    }
}