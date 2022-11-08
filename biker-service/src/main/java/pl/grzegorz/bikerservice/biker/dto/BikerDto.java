package pl.grzegorz.bikerservice.biker.dto;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class BikerDto {

    @NotBlank(message = "First name filed must not be null/empty")
    private String firstName;
    @NotBlank(message = "Last name filed must not be null/empty")
    private String lastName;
    @Email(message = "Wrong email value")
    private String email;
    private String dateOfBirth;
}
