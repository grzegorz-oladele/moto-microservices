package pl.grzegorz.motorcycleservice.motorcycle_class;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.grzegorz.motorcycleservice.motorcycle_class.dto.input.MotorcycleClassDto;
import pl.grzegorz.motorcycleservice.motorcycle_class.dto.output.MotorcycleClassOutputDto;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static pl.grzegorz.motorcycleservice.motorcycle_class.MotorcycleClassTestInitValue.*;


@ExtendWith(MockitoExtension.class)
class MotorcycleClassFacadeImplTest {

    @InjectMocks
    private MotorcycleClassFacadeImpl motorcycleClassFacade;
    @Mock
    private MotorcycleClassRepository motorcycleClassRepository;
    @Mock
    private MotorcycleClassQueryRepository motorcycleClassQueryRepository;

    private final long motorcycleClassId = 2;

    private MotorcycleClassDto motorcycleClassDto;
    private MotorcycleClassEntity motorcycleClass;

    @BeforeEach
    void setup() {
        motorcycleClassDto = getUnitTestingMotorcycleClassDto();
        motorcycleClass = getUnitTestingMotorcycleClassEntity();
    }

    @Test
    void shouldReturnListOfMotorcycleClassOutputDto() {
//        given
        List<MotorcycleClassOutputDto> classesOfMotorcycles = getUnitTestingListOfMotorcycleClassOutputDto();
        when(motorcycleClassQueryRepository.findAllBy()).thenReturn(classesOfMotorcycles);
//        when
        List<MotorcycleClassOutputDto> motorcycleClassList = motorcycleClassFacade.getMotorcycleClassList();
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
        motorcycleClassFacade.addMotorcyclesClass(motorcycleClassDto);
//        then
        verify(motorcycleClassRepository).save(any(MotorcycleClassEntity.class));
    }

    @Test
    void shouldCallingSaveMethodFromMotorcycleClassRepository() {
//        given
        when(motorcycleClassRepository.findById(motorcycleClassId)).thenReturn(Optional.of(motorcycleClass));
//        when
        motorcycleClassFacade.editMotorcycleClass(motorcycleClassId, motorcycleClassDto);
//        then
        verify(motorcycleClassRepository).save(any(MotorcycleClassEntity.class));
    }

    @Test
    void shouldCallingDeleteMethodFromMotorcycleClassRepository() {
//        given
        when(motorcycleClassRepository.findById(motorcycleClassId)).thenReturn(Optional.of(motorcycleClass));
//        when
        motorcycleClassFacade.removeMotorcycleClassById(motorcycleClassId);
//        then
        verify(motorcycleClassRepository).delete(any(MotorcycleClassEntity.class));
    }
}