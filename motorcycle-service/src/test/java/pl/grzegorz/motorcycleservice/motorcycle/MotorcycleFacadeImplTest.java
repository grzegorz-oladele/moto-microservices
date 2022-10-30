package pl.grzegorz.motorcycleservice.motorcycle;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import pl.grzegorz.motorcycleservice.bike_class.BikeClassFacade;
import pl.grzegorz.motorcycleservice.bike_class.query.BikeClassSimpleEntity;
import pl.grzegorz.motorcycleservice.motorcycle.dto.input.MotorcycleDto;
import pl.grzegorz.motorcycleservice.motorcycle.dto.output.MotorcycleOutputDto;
import pl.grzegorz.motorcycleservice.tools.validator.ValidatorFacade;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static pl.grzegorz.motorcycleservice.bike_class.BikeClassTestInitValue.getUnitTestBikeClassSimpleEntity;
import static pl.grzegorz.motorcycleservice.motorcycle.UnitTestMotorcycleInitValue.getMotorcycleEntity;
import static pl.grzegorz.motorcycleservice.motorcycle.UnitTestMotorcycleInitValue.getUnitTestingListOfMotorcycleOutputDto;

@ExtendWith(MockitoExtension.class)
class MotorcycleFacadeImplTest {

    @InjectMocks
    private MotorcycleFacadeImpl motorcycleFacade;
    @Mock
    private MotorcycleRepository motorcycleRepository;
    @Mock
    private MotorcycleQueryRepository motorcycleQueryRepository;
    @Mock
    private BikeClassFacade bikeClassFacade;
    @Mock
    private ValidatorFacade validatorFacade;

    private final long motorcycleId = 2;

    private MotorcycleDto motorcycleDto;
    private MotorcycleEntity motorcycle;
    private List<MotorcycleOutputDto> motorcycleList;
    private BikeClassSimpleEntity bikeClassSimpleEntity;

    @BeforeEach
    void setup() {
        motorcycleDto = MotorcycleDto.builder()
                .brand("Yamaha")
                .model("YZF R1")
                .capacity(999)
                .horsePower(205)
                .vintage(2021)
                .build();
        motorcycle = getMotorcycleEntity();
        motorcycleList = getUnitTestingListOfMotorcycleOutputDto();
        bikeClassSimpleEntity = getUnitTestBikeClassSimpleEntity();
    }

    @Test
    void shouldReturnListOfMotorcycleOutputDtoObjects() {
        int page = 1;
        int size = 2;
        when(motorcycleQueryRepository.findAllBy(PageRequest.of(0, size))).thenReturn(motorcycleList);
        doNothing().when(validatorFacade).checkPageAndSizeValueAndThrowExceptionIfIsWrong(page, size);
//        when
        List<MotorcycleOutputDto> motorcycleOutputDtoList = motorcycleFacade.getListOfMotorcycles(page, size);
//        then
        assertAll(
                () -> assertNotNull(motorcycleOutputDtoList),
                () -> assertEquals(2, motorcycleOutputDtoList.size()),
                () -> assertEquals(1, motorcycleOutputDtoList.get(0).getId()),
                () -> assertEquals("Ducati", motorcycleOutputDtoList.get(0).getBrand()),
                () -> assertEquals("Panigale V2", motorcycleOutputDtoList.get(0).getModel()),
                () -> assertEquals(965, motorcycleOutputDtoList.get(0).getCapacity()),
                () -> assertEquals(165, motorcycleOutputDtoList.get(0).getHorsePower()),
                () -> assertEquals(2021, motorcycleOutputDtoList.get(0).getVintage()),
                () -> assertEquals(1, motorcycleOutputDtoList.get(0).getMotorcycleClass().getId()),
                () -> assertEquals("Super-sport", motorcycleOutputDtoList.get(0).getMotorcycleClass().getName()),
                () -> assertEquals("1", motorcycleOutputDtoList.get(0).getBikerId()),
                () -> assertEquals("Triumph", motorcycleOutputDtoList.get(1).getBrand()),
                () -> assertEquals("Street triple 765R", motorcycleOutputDtoList.get(1).getModel()),
                () -> assertEquals(765, motorcycleOutputDtoList.get(1).getCapacity()),
                () -> assertEquals(128, motorcycleOutputDtoList.get(1).getHorsePower()),
                () -> assertEquals(2021, motorcycleOutputDtoList.get(1).getVintage()),
                () -> assertEquals(2, motorcycleOutputDtoList.get(1).getMotorcycleClass().getId()),
                () -> assertEquals("Naked", motorcycleOutputDtoList.get(1).getMotorcycleClass().getName()),
                () -> assertEquals("3", motorcycleOutputDtoList.get(1).getBikerId())
        );
    }

    @Test
    void shouldThrowExceptionWhenMotorcycleWillBeNotFoundInTheDatabase() {
//        given
//        when + then
        assertAll(
                () -> assertThrows(IllegalArgumentException.class,
                        () -> motorcycleFacade.getMotorcycleById(motorcycleId)),
                () -> {
                    try {
                        motorcycleFacade.getMotorcycleById(motorcycleId);
                    }
                    catch (IllegalArgumentException e) {
                        assertEquals("Motorcycle not found", e.getMessage());
                    }
                }
        );
    }

    @Test
    void shouldReturnMotorcycleOutputDtoObject() {
//        given
        when(motorcycleQueryRepository.findAllById(motorcycleId)).thenReturn(Optional.of(motorcycleList.get(0)));
//        when
        MotorcycleOutputDto motorcycleById = motorcycleFacade.getMotorcycleById(motorcycleId);
//        then
        assertAll(
                () -> assertNotNull(motorcycleById),
                () -> assertEquals(1, motorcycleById.getId()),
                () -> assertEquals("Ducati", motorcycleById.getBrand()),
                () -> assertEquals("Panigale V2", motorcycleById.getModel()),
                () -> assertEquals(965, motorcycleById.getCapacity()),
                () -> assertEquals(165, motorcycleById.getHorsePower()),
                () -> assertEquals(2021, motorcycleById.getVintage()),
                () -> assertEquals(1, motorcycleById.getMotorcycleClass().getId())
        );
    }

    @Test
    void shouldCallingSaveMethodFromMotorcycleRepositoryInterface() {
//        given
        long bikeClassId = 3;
        when(bikeClassFacade.getBikeClassSimpleEntity(bikeClassId)).thenReturn(bikeClassSimpleEntity);
//        when
        motorcycleFacade.addMotorcycle(motorcycleDto, bikeClassId);
//        then
        verify(motorcycleRepository).save(any(MotorcycleEntity.class));
    }

    @Test
    void shouldCallingSaveMethodFromMotorcycleRepository() {
//        given
        when(motorcycleRepository.findById(motorcycleId)).thenReturn(Optional.of(motorcycle));
//        when
        motorcycleFacade.editMotorcycle(motorcycleId, motorcycleDto);
//        then
        verify(motorcycleRepository).save(any(MotorcycleEntity.class));
    }

    @Test
    void shouldCallingDeleteMethodFromMotorcycleRepositoryInterface() {
//        given
        when(motorcycleRepository.findById(motorcycleId)).thenReturn(Optional.of(motorcycle));
//        when
        motorcycleFacade.removeMotorcycle(motorcycleId);
//        then
        verify(motorcycleRepository).delete(any(MotorcycleEntity.class));
    }
}