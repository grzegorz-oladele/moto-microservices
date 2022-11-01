package pl.grzegorz.bikerservice.tools.validator;

import pl.grzegorz.bikerservice.biker.dto.BikerDto;

public interface ValidatorFacade {

    void checkPageAndSizeValueAndThrowExceptionIfIsWrong(int page, int size);
    void checkDateOfBirthAndThrowExceptionIfIsWrong(BikerDto bikerDto);
}
