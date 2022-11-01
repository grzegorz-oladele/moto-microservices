package pl.grzegorz.bikerservice.biker;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.grzegorz.bikerservice.biker.dto.BikerDto;
import pl.grzegorz.bikerservice.biker.dto.BikerOutputDto;
import pl.grzegorz.bikerservice.role.RoleFacade;
import pl.grzegorz.bikerservice.tools.validator.ValidatorFacade;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static java.lang.Boolean.TRUE;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.data.domain.PageRequest.of;
import static pl.grzegorz.bikerservice.biker.BikerInitTestValue.getBikerDto;
import static pl.grzegorz.bikerservice.biker.BikerInitTestValue.getListOfBikers;


@ExtendWith(MockitoExtension.class)
class BikerFacadeImplTest {

    @InjectMocks
    private BikerFacadeImpl bikerFacade;
    @Mock
    private BikerRepository bikerRepository;
    @Mock
    private BikerQueryRepository bikerQueryRepository;
    @Mock
    private RoleFacade roleFacade;
    @Mock
    private ValidatorFacade validatorFacade;

    private final long bikerId = 2;
    private List<BikerOutputDto> bikers;
    private BikerDto bikerDto;

    @BeforeEach
    void setup() {
        bikers = getListOfBikers();
        bikerDto = getBikerDto();
    }


    @Test
    void shouldReturnListOfBikerOutputDtoObjects() {
//        given
        int page = 1;
        int size = 2;
        when(bikerQueryRepository.getAllBy(of(0, size))).thenReturn(bikers);
        doNothing().when(validatorFacade).checkPageAndSizeValueAndThrowExceptionIfIsWrong(page, size);
//        when
        List<BikerOutputDto> bikers = bikerFacade.getAllBikers(page, size);
//        then
        assertAll(
                () -> assertNotNull(bikers),
                () -> assertEquals(2, bikers.size()),
                () -> assertEquals(1L, bikers.get(0).getId()),
                () -> assertEquals("Tomasz", bikers.get(0).getFirstName()),
                () -> assertEquals("Tomaszewski", bikers.get(0).getLastName()),
                () -> assertEquals(LocalDate.of(1989, 8, 12), bikers.get(0).getDateOfBirth()),
                () -> assertEquals(TRUE, bikers.get(0).getIsActive()),
                () -> assertEquals(2L, bikers.get(1).getId()),
                () -> assertEquals("Adam", bikers.get(1).getFirstName()),
                () -> assertEquals("Adamowicz", bikers.get(1).getLastName()),
                () -> assertEquals(LocalDate.of(1984, 12, 27), bikers.get(1).getDateOfBirth()),
                () -> assertEquals(TRUE, bikers.get(1).getIsActive())
                );
    }

    @Test
    void shouldThrowExceptionWhenBikerWillBeNotFoundInTheDatabase() {
//        given
//        when + then
        assertAll(
                () -> assertThrows(IllegalArgumentException.class,
                        () -> bikerFacade.getBikerById(bikerId)),
                () -> {
                    try {
                        bikerFacade.getBikerById(bikerId);
                    }
                    catch (IllegalArgumentException e) {
                        assertEquals("Biker not found", e.getMessage());
                    }
                }
        );
    }

    @Test
    void shouldReturnBikerOutputDtoObject() {
//        given
        when(bikerQueryRepository.findAllById(2)).thenReturn(Optional.of(bikers.get(1)));
//        when
        BikerOutputDto bikerById = bikerFacade.getBikerById(bikerId);
        assertAll(
                () -> assertNotNull(bikerById),
                () -> assertEquals(2L, bikers.get(1).getId()),
                () -> assertEquals("Adam", bikers.get(1).getFirstName()),
                () -> assertEquals("Adamowicz", bikers.get(1).getLastName()),
                () -> assertEquals(LocalDate.of(1984, 12, 27), bikers.get(1).getDateOfBirth()),
                () -> assertEquals(TRUE, bikers.get(1).getIsActive())
        );
    }

    @Test
    void shouldCallingSaveMethodFromBikerRepositoryInterfaceWhenWillBeAddNewBiker() {
//        given
        String roleName = "USER";
        when(roleFacade.existsByName(roleName)).thenReturn(true);
//        when
        bikerFacade.addNewBiker(bikerDto);
//        then
        verify(bikerRepository).save(any(BikerEntity.class));
    }

    @Test
    void shouldCallingSaveMethodFromBikerRepositoryWhenWeEditBiker() {
//        given
        String roleName = "USER";
        when(roleFacade.existsByName(roleName)).thenReturn(true);
        when(bikerRepository.existsById(bikerId)).thenReturn(true);
//        when
        bikerFacade.editBiker(bikerId, bikerDto);
//        then
        verify(bikerRepository).save(any(BikerEntity.class));
    }

    @Test
    void shouldCallingDeleteMethodFromBikerRepositoryWhenWeRemoveBiker() {
//        given
        when(bikerRepository.existsById(bikerId)).thenReturn(true);
//        when
        bikerFacade.removeBiker(bikerId);
//        then
        verify(bikerRepository).deleteById(bikerId);
    }
}