package pl.grzegorz.motorcycleservice.tools.validator;

public interface ValidatorFacade {

    void checkPageAndSizeValueAndThrowExceptionIfIsWrong(int page, int size);
}
