package pl.grzegorz.motorcycleservice.tools.validator;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ValidatorFacadeImplTest {

    @InjectMocks
    private ValidatorFacadeImpl validatorFacade;

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
}