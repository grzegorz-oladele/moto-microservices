package pl.grzegorz.bikerservice.tools.validator;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import pl.grzegorz.bikerservice.biker.dto.BikerDto;

import java.time.LocalDate;

import static java.time.LocalDate.*;
import static lombok.AccessLevel.PRIVATE;

@Component
@NoArgsConstructor(access = PRIVATE)
@Slf4j
class ValidatorFacadeImpl implements ValidatorFacade {

    private static final LocalDate CRITICAL_DATE = of(1900, 1, 1);

    @Override
    public void checkPageAndSizeValueAndThrowExceptionIfIsWrong(int page, int size) {
        if (page <= 0 || size <= 0) {
            log.error("Wrong page or size value");
            throw new IllegalArgumentException("Wrong page or size value. Page and size must be greater than 0");
        }
    }

    @Override
    public void checkDateOfBirthAndThrowExceptionIfIsWrong(BikerDto bikerDto) {
        checkDateOfBirthAndThrowExceptionIfBefore(bikerDto);
        checkDateOfBirthAndThrowExceptionIfEqualOrBefore(bikerDto);
    }

    private void checkDateOfBirthAndThrowExceptionIfEqualOrBefore(BikerDto bikerDto) {
        LocalDate date = parseDateOfBirth(bikerDto);
        if (date.isEqual(CRITICAL_DATE) ||
                date.isBefore(CRITICAL_DATE)) {
            log.error("Wrong date of birth by {} {}; date of birth is equal or before the current date",
                    bikerDto.getFirstName(), bikerDto.getLastName());
            throw new IllegalArgumentException("Date of birth mus be after 1990-01-01");
        }
    }

    private void checkDateOfBirthAndThrowExceptionIfBefore(BikerDto bikerDto) {
        LocalDate date = parseDateOfBirth(bikerDto);
        if (date.isAfter(now())) {
            log.error("Wrong date of birth by {} {}; date of birth is after current the date", bikerDto.getFirstName(),
                    bikerDto.getLastName());
            throw new IllegalArgumentException("Date of birth must be before the current date");
        }
    }

    private LocalDate parseDateOfBirth(BikerDto bikerDto) {
        return parse(bikerDto.getDateOfBirth());
    }
}
