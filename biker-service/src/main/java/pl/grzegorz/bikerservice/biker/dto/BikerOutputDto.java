package pl.grzegorz.bikerservice.biker.dto;

import java.time.LocalDate;

public interface BikerOutputDto {

    Long getId();
    String getFirstName();
    String getLastName();
    LocalDate getDateOfBirth();
    Boolean getIsActive();
}
