package pl.grzegorz.bikerservice.tools.validator;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.grzegorz.bikerservice.biker.dto.BikerDto;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ValidatorFacadeImplTest {

    @InjectMocks
    private ValidatorFacadeImpl validatorFacade;

    private BikerDto bikerDto;

    @Test
    void shouldThrowExceptionWhenPageValueWillBeLessThanZero() {
//        given
        int page = 0;
        int size = 1;
//        when + then
        assertAll(
                () -> assertThrows(IllegalArgumentException.class,
                        () -> validatorFacade.checkPageAndSizeValueAndThrowExceptionIfIsWrong(page, size)),
                () -> {
                    try {
                        validatorFacade.checkPageAndSizeValueAndThrowExceptionIfIsWrong(page, size);
                    }
                    catch (IllegalArgumentException e) {
                        assertEquals("Wrong page or size value. Page and size must be greater than 0",
                                e.getMessage());
                    }
                }
        );
    }

    @Test
    void shouldThrowExceptionWhenSizeValueWillBeLessThanZero() {
//        given
        int page = 1;
        int size = 0;
//        when + then
        assertAll(
                () -> assertThrows(IllegalArgumentException.class,
                        () -> validatorFacade.checkPageAndSizeValueAndThrowExceptionIfIsWrong(page, size)),
                () -> {
                    try {
                        validatorFacade.checkPageAndSizeValueAndThrowExceptionIfIsWrong(page, size);
                    }
                    catch (IllegalArgumentException e) {
                        assertEquals("Wrong page or size value. Page and size must be greater than 0",
                                e.getMessage());
                    }
                }
        );
    }

    @Test
    void shouldThrowExceptionWhenDateOfBirthWillBeBeforeCriticalDate() {
//        given
        bikerDto = BikerDto.builder()
                .dateOfBirth("1899-12-31")
                .build();
//        when + then
        assertAll(
                () -> assertThrows(IllegalArgumentException.class,
                        () -> validatorFacade.checkDateOfBirthAndThrowExceptionIfIsWrong(bikerDto)),
                () -> {
                    try {
                        validatorFacade.checkDateOfBirthAndThrowExceptionIfIsWrong(bikerDto);
                    }
                    catch (IllegalArgumentException e) {
                        assertEquals("Date of birth mus be after 1990-01-01", e.getMessage());
                    }
                }
        );
    }

    @Test
    void shouldThrowExceptionWhenDateOfBirthWillBeEqualCriticalDate() {
//        given
        bikerDto = BikerDto.builder()
                .dateOfBirth("1900-01-01")
                .build();
//        when + then
        assertAll(
                () -> assertThrows(IllegalArgumentException.class,
                        () -> validatorFacade.checkDateOfBirthAndThrowExceptionIfIsWrong(bikerDto)),
                () -> {
                    try {
                        validatorFacade.checkDateOfBirthAndThrowExceptionIfIsWrong(bikerDto);
                    }
                    catch (IllegalArgumentException e) {
                        assertEquals("Date of birth mus be after 1990-01-01", e.getMessage());
                    }
                }
        );
    }

    @Test
    void shouldThrowExceptionWhenDateOfBirthWillBeAfterCurrentDate() {
//        given
        bikerDto = BikerDto.builder()
                .dateOfBirth("2024-12-31")
                .build();
//        when + then
        assertAll(
                () -> assertThrows(IllegalArgumentException.class,
                        () -> validatorFacade.checkDateOfBirthAndThrowExceptionIfIsWrong(bikerDto)),
                () -> {
                    try {
                        validatorFacade.checkDateOfBirthAndThrowExceptionIfIsWrong(bikerDto);
                    }
                    catch (IllegalArgumentException e) {
                        assertEquals("Date of birth must be before the current date", e.getMessage());
                    }
                }
        );
    }
}