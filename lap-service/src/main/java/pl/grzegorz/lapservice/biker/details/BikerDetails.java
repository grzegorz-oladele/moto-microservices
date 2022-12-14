package pl.grzegorz.lapservice.biker.details;

import lombok.*;

import static lombok.AccessLevel.*;

@Getter
@AllArgsConstructor(access = PRIVATE)
@Builder
public class BikerDetails {

    private Long id;
    private String firstName;
    private String lastName;
    private String username;
}