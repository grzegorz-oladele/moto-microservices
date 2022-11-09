package pl.grzegorz.circuitservice.circuit.dto.input;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import static lombok.AccessLevel.PRIVATE;

@Getter
@Setter
@AllArgsConstructor(access = PRIVATE)
@Builder
public class CircuitDto {

    private String name;
    private String description;
    private String city;
    private String postalCode;
    private String street;
    private String streetNumber;
    private String email;
    private String phoneNumber;
    private String type;
}
