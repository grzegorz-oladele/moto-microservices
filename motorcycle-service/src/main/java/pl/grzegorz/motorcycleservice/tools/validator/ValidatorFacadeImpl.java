package pl.grzegorz.motorcycleservice.tools.validator;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Slf4j
class ValidatorFacadeImpl implements ValidatorFacade {

    @Override
    public void checkPageAndSizeValueAndThrowExceptionIfIsWrong(int page, int size) {
        if (page <= 0 || size <= 0) {
            log.error("Wrong page or size value");
            throw new IllegalArgumentException("Wrong page or size value. Page and size must be greater than 0");
        }
    }

    @Override
    public void checkVintageValueAndThrowExceptionIfIsWrong(int vintage) {
        if (vintage > LocalDate.now().getYear() - 1) {
            throw new IllegalArgumentException("Vintage field has wrong value. Vintage file may not have more value " +
                    "than previous year");
        }
    }
}