package pl.grzegorz.motorcycleservice.bike_class;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.grzegorz.motorcycleservice.bike_class.dto.input.BikeClassDto;
import pl.grzegorz.motorcycleservice.bike_class.dto.output.BikeClassOutputDto;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static pl.grzegorz.motorcycleservice.bike_class.MotorcycleClassTestInitValue.*;


@ExtendWith(MockitoExtension.class)
class BikeClassFacadeImplTest {

    @InjectMocks
    private BikeClassFacadeImpl motorcycleClassFacade;
    @Mock
    private BikeClassRepository bikeClassRepository;
    @Mock
    private BikeClassQueryRepository bikeClassQueryRepository;

    private final long motorcycleClassId = 2;

    private BikeClassDto bikeClassDto;
    private BikeClassEntity motorcycleClass;

    @BeforeEach
    void setup() {
        bikeClassDto = getUnitTestingMotorcycleClassDto();
        motorcycleClass = getFirstMotorcycleClassEntity();
    }

    @Test
    void shouldReturnListOfMotorcycleClassOutputDto() {
//        given
        List<BikeClassOutputDto> classesOfMotorcycles = getUnitTestingListOfMotorcycleClassOutputDto();
        when(bikeClassQueryRepository.findAllBy()).thenReturn(classesOfMotorcycles);
//        when
        List<BikeClassOutputDto> motorcycleClassList = motorcycleClassFacade.getMotorcycleClassList();
//        then
        assertAll(
                () -> assertNotNull(motorcycleClassList),
                () -> assertEquals(2, motorcycleClassList.size()),
                () -> assertEquals(1L, motorcycleClassList.get(0).getId()),
                () -> assertEquals("Super Sport", motorcycleClassList.get(0).getName()),
                () -> assertEquals("Sports motorcycles with large engine capacity",
                        motorcycleClassList.get(0).getDescription()),
                () -> assertEquals(2L, motorcycleClassList.get(1).getId()),
                () -> assertEquals("Naked", motorcycleClassList.get(1).getName()),
                () -> assertEquals("City motorcycles with sporty flair",
                        motorcycleClassList.get(1).getDescription())
        );
    }

    @Test
    void shouldThrowExceptionWhenMotorcycleClassWillBeNotFoundInTheDatabase() {
//        given
//        when + then
        assertAll(
                () -> assertThrows(IllegalArgumentException.class,
                        () -> motorcycleClassFacade.getMotorcycleClassById(motorcycleClassId)),
                () -> {
                    try {
                        motorcycleClassFacade.getMotorcycleClassById(motorcycleClassId);
                    } catch (IllegalArgumentException e) {
                        assertEquals("Motorcycle class not found", e.getMessage());
                    }
                }
        );
    }

    @Test
    void shouldCallingSaveMethodFromMotorcycleClassRepositoryInterface() {
//        given
//        when
        motorcycleClassFacade.addMotorcyclesClass(bikeClassDto);
//        then
        verify(bikeClassRepository).save(any(BikeClassEntity.class));
    }

    @Test
    void shouldCallingSaveMethodFromMotorcycleClassRepository() {
//        given
        when(bikeClassRepository.findById(motorcycleClassId)).thenReturn(Optional.of(motorcycleClass));
//        when
        motorcycleClassFacade.editMotorcycleClass(motorcycleClassId, bikeClassDto);
//        then
        verify(bikeClassRepository).save(any(BikeClassEntity.class));
    }

    @Test
    void shouldCallingDeleteMethodFromMotorcycleClassRepository() {
//        given
        when(bikeClassRepository.findById(motorcycleClassId)).thenReturn(Optional.of(motorcycleClass));
//        when
        motorcycleClassFacade.removeMotorcycleClassById(motorcycleClassId);
//        then
        verify(bikeClassRepository).delete(any(BikeClassEntity.class));
    }
}