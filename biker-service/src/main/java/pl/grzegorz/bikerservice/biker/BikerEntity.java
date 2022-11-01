package pl.grzegorz.bikerservice.biker;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import pl.grzegorz.bikerservice.biker.dto.BikerDto;
import pl.grzegorz.bikerservice.role.query.RoleSimpleEntity;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

import static java.lang.Boolean.TRUE;
import static javax.persistence.CascadeType.*;
import static javax.persistence.FetchType.*;

@Entity
@Table(name = "bikers")
@Getter(value = AccessLevel.PROTECTED)
@Setter(value = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
class BikerEntity {

    @Id
    @GeneratedValue(generator = "sequence_generator")
    @GenericGenerator(
            name = "sequence_generator",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @org.hibernate.annotations.Parameter(name = "sequence_name", value = "motorcycle_class_sequence"),
                    @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
                    @org.hibernate.annotations.Parameter(name = "increment_value", value = "1")
            }
    )
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private LocalDate dateOfBirth;
    @ManyToMany(cascade = {ALL, MERGE}, fetch = EAGER)
    @JoinTable(name = "biker_roles", joinColumns = @JoinColumn(name = "biker_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<RoleSimpleEntity> roles;
    private Boolean isActive;

    static BikerEntity toBikerEntity(BikerDto bikerDto) {
        return BikerEntity.builder()
                .firstName(bikerDto.getFirstName())
                .lastName(bikerDto.getLastName())
                .email(bikerDto.getEmail())
                .dateOfBirth(LocalDate.parse(bikerDto.getDateOfBirth()))
                .isActive(TRUE)
                .build();
    }
}