package pl.grzegorz.motorcycleservice.tools.validator;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

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
}