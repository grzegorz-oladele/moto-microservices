package pl.grzegorz.bikerservice.biker;

import lombok.NoArgsConstructor;
import pl.grzegorz.bikerservice.biker.dto.BikerDto;
import pl.grzegorz.bikerservice.biker.dto.BikerOutputDto;
import pl.grzegorz.bikerservice.role.query.RoleSimpleEntity;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import static java.lang.Boolean.TRUE;
import static java.util.Arrays.asList;
import static java.util.List.*;
import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
class BikerInitTestValue {

    static List<BikerOutputDto> getListOfBikers() {
        BikerOutputDto firstBiker = new BikerOutputDto() {
            @Override
            public Long getId() {
                return 1L;
            }

            @Override
            public String getFirstName() {
                return "Tomasz";
            }

            @Override
            public String getLastName() {
                return "Tomaszewski";
            }

            @Override
            public LocalDate getDateOfBirth() {
                return LocalDate.of(1989, 8, 12);
            }

            @Override
            public Boolean getIsActive() {
                return TRUE;
            }
        };

        BikerOutputDto secondBiker = new BikerOutputDto() {
            @Override
            public Long getId() {
                return 2L;
            }

            @Override
            public String getFirstName() {
                return "Adam";
            }

            @Override
            public String getLastName() {
                return "Adamowicz";
            }

            @Override
            public LocalDate getDateOfBirth() {
                return LocalDate.of(1984, 12, 27);
            }

            @Override
            public Boolean getIsActive() {
                return TRUE;
            }
        };
        return asList(firstBiker, secondBiker);
    }

    static BikerDto getBikerDto() {
        return BikerDto.builder()
                .firstName("Paweł")
                .lastName("Pawłowski")
                .dateOfBirth("1991-09-21")
                .email("paweł@pawłowski.pl")
                .roles(of("USER"))
                .build();
    }

    static BikerEntity getFirstBikerEntity() {
        return BikerEntity.builder()
                .firstName("Paweł")
                .lastName("Pawłowski")
                .dateOfBirth(LocalDate.of(1991,9,21))
                .email("paweł@pawłowski.pl")
                .roles(Set.of(getRoleSimpleEntity()))
                .build();
    }

    static BikerEntity getSecondBikerEntity() {
        return BikerEntity.builder()
                .firstName("Adam")
                .lastName("Adamowicz")
                .dateOfBirth(LocalDate.of(1984,12,27))
                .email("adam@adamowicz.pl")
                .roles(Set.of(getRoleSimpleEntity()))
                .build();
    }

    static RoleSimpleEntity getRoleSimpleEntity() {
        return RoleSimpleEntity.builder()
                .name("USER")
                .build();
    }
}
