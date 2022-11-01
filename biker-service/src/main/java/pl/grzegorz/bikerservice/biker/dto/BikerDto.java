package pl.grzegorz.bikerservice.biker.dto;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

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
//    @Past(message = "Date of birth must be less than today")
//    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private String dateOfBirth;
    @NotNull(message = "List of roles must not be null")
    @Size(min = 1, message = "Size list of roles must be greater than 0")
    private List<String> roles;
}
