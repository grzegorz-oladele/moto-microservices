package pl.grzegorz.circuitservice.circuit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import pl.grzegorz.circuitservice.circuit.dto.input.CircuitDto;
import pl.grzegorz.circuitservice.circuit.dto.output.CircuitOutputDto;
import pl.grzegorz.circuitservice.circuit.query.CircuitSimpleEntity;

import java.util.List;

import static java.util.Optional.of;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static pl.grzegorz.circuitservice.circuit.CircuitTestInitValue.*;


@ExtendWith(MockitoExtension.class)
class CircuitFacadeImplTest {

    @InjectMocks
    private CircuitFacadeImpl circuitFacade;
    @Mock
    private CircuitRepository circuitRepository;
    @Mock
    private CircuitQueryRepository circuitQueryRepository;

    private final long circuitId = 1;

    private List<CircuitOutputDto> circuits;
    private CircuitEntity circuit;
    private CircuitDto circuitDto;

    @BeforeEach
    void setup() {
        circuits = getListOfCircuitOutputDto();
        circuit = getCircuitEntity();
        circuitDto = getCircuitDto();
    }

    @Test
    void shouldReturnListOfCircuitOutputDtoObjects() {
//        given
        int page = 1;
        int size = 2;
        when(circuitQueryRepository.findAllBy(PageRequest.of(0, size))).thenReturn(circuits);
//        when
        List<CircuitOutputDto> allCircuits = circuitFacade.getAllCircuits(page, size);
//        then
        assertAll(
                () -> assertNotNull(allCircuits),
                () -> assertEquals(2, allCircuits.size()),
                () -> assertEquals(1L, allCircuits.get(0).getId()),
                () -> assertEquals("Tor Poznań", allCircuits.get(0).getName()),
                () -> assertEquals("Największy tor w Polsce", allCircuits.get(0).getDescription()),
                () -> assertEquals("Poznań", allCircuits.get(0).getCity()),
                () -> assertEquals("00-000", allCircuits.get(0).getPostalCode()),
                () -> assertEquals("Poznańska", allCircuits.get(0).getStreet()),
                () -> assertEquals("3", allCircuits.get(0).getStreetNumber()),
                () -> assertEquals("tor@poznan.pl", allCircuits.get(0).getEmail()),
                () -> assertEquals("123-456-789", allCircuits.get(0).getPhoneNumber()),
                () -> assertEquals(0, allCircuits.get(0).getListOfTracks().size()),
                () -> assertEquals(2L, allCircuits.get(1).getId()),
                () -> assertEquals("Tor Jastrząb", allCircuits.get(1).getName()),
                () -> assertEquals("Tor w okolicy Radomia", allCircuits.get(1).getDescription()),
                () -> assertEquals("Jastrząb", allCircuits.get(1).getCity()),
                () -> assertEquals("Czerwienica", allCircuits.get(1).getStreet()),
                () -> assertEquals("25", allCircuits.get(1).getStreetNumber()),
                () -> assertEquals("tor@jastrzab.pl", allCircuits.get(1).getEmail()),
                () -> assertEquals("987-654-321", allCircuits.get(1).getPhoneNumber()),
                () -> assertEquals(0, allCircuits.get(1).getListOfTracks().size())
        );
    }

    @Test
    void shouldReturnSingleCircuitOutputDtoObject() {
//        given
        when(circuitQueryRepository.findAllById(circuitId)).thenReturn(of(circuits.get(0)));
//        when
        CircuitOutputDto circuitById = circuitFacade.getCircuitById(circuitId);
        assertAll(
                () -> assertNotNull(circuitById),
                () -> assertEquals(1L, circuitById.getId()),
                () -> assertEquals("Tor Poznań", circuitById.getName()),
                () -> assertEquals("Największy tor w Polsce", circuitById.getDescription()),
                () -> assertEquals("Poznań", circuitById.getCity()),
                () -> assertEquals("00-000", circuitById.getPostalCode()),
                () -> assertEquals("Poznańska", circuitById.getStreet()),
                () -> assertEquals("3", circuitById.getStreetNumber()),
                () -> assertEquals("tor@poznan.pl", circuitById.getEmail()),
                () -> assertEquals("123-456-789", circuitById.getPhoneNumber())
        );
    }

    @Test
    void shouldReturnCircuitSimpleEntityObject() {
//        given
        when(circuitRepository.findById(circuitId)).thenReturn(of(circuit));
//        when
        CircuitSimpleEntity circuitSimpleEntityById = circuitFacade.getCircuitSimpleEntityById(circuitId);
//        then
        assertAll(
                () -> assertNotNull(circuitSimpleEntityById),
                () -> assertEquals(1L, circuitSimpleEntityById.getId()),
                () -> assertEquals("Tor Poznań", circuitSimpleEntityById.getName()),
                () -> assertEquals(0, circuitSimpleEntityById.getListOfCircuits().size())
        );
    }

    @Test
    void shouldCallSaveMethodFromCircuitRepositoryByAddNewCircuitToTheDatabase() {
//        given
//        when
        circuitFacade.addNewCircuit(circuitDto);
//        then
        verify(circuitRepository).save(any(CircuitEntity.class));
    }

    @Test
    void shouldCallSaveMethodFromCircuitRepositoryByEditCircuitById() {
//        given
        when(circuitRepository.findById(circuitId)).thenReturn(of(circuit));
//        when
        circuitFacade.editCircuit(circuitId, circuitDto);
//        then
        verify(circuitRepository).save(any(CircuitEntity.class));
    }

    @Test
    void shouldDeleteMethodFromCircuitRepository() {
//        given
        when(circuitRepository.existsById(circuitId)).thenReturn(true);
//        when
        circuitFacade.removeCircuit(circuitId);
//        then
        verify(circuitRepository).deleteById(circuitId);
    }
}