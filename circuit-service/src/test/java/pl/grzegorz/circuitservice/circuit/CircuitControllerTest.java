package pl.grzegorz.circuitservice.circuit;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import pl.grzegorz.circuitservice.BaselineIntegrationTest;
import pl.grzegorz.circuitservice.circuit.dto.input.CircuitDto;
import pl.grzegorz.circuitservice.track.query.TrackSimpleEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static pl.grzegorz.circuitservice.circuit.CircuitTestInitValue.*;

class CircuitControllerTest extends BaselineIntegrationTest {

    private static final String URL = "/circuits";
    private static final long WRONG_ID = 100;
    private static final int page = 1;
    private static final int size = 2;

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private CircuitRepository circuitRepository;
    @Autowired
    private ObjectMapper objectMapper;

    private static CircuitEntity firstCircuitEntity;
    private static CircuitEntity secondCircuitEntity;
    private static CircuitDto circuitDto;

    @BeforeAll
    static void start() {
        firstCircuitEntity = getCircuitEntity();
        secondCircuitEntity = getSecondCircuitEntity();
        circuitDto = getSecondCircuitDto();
    }

    @BeforeEach
    void setupDb() {
        circuitRepository.save(firstCircuitEntity);
    }

    @AfterEach
    void clearDb() {
        circuitRepository.deleteAll();
    }

    @Test
    void shouldReturnSingleObjectOfCircuitDtoOutput() throws Exception {
//        given
        CircuitEntity circuit = circuitRepository.save(firstCircuitEntity);
        long id = circuit.getId();
//        when
//        WHEN CIRCUIT NOT FOUND
        mockMvc.perform(get(URL + "/" + WRONG_ID))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andReturn();

//        WHEN CIRCUIT WAS FOUND
        String result = mockMvc.perform(get(URL + "/" + id))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        CircuitOutputDto circuitOutputDto = objectMapper.readValue(result, CircuitOutputDto.class);
//        then
        assertAll(
                () -> assertNotNull(circuitOutputDto),
                () -> assertEquals("Tor Poznan", circuitOutputDto.getName()),
                () -> assertEquals("Najwiekszy tor w Polsce", circuitOutputDto.getDescription()),
                () -> assertEquals("Poznan", circuitOutputDto.getCity()),
                () -> assertEquals("00-000", circuitOutputDto.getPostalCode()),
                () -> assertEquals("Poznanska", circuitOutputDto.getStreet()),
                () -> assertEquals("3", circuitOutputDto.getStreetNumber()),
                () -> assertEquals("tor@poznan.pl", circuitOutputDto.getEmail()),
                () -> assertEquals("123-456-789", circuitOutputDto.getPhoneNumber()),
                () -> assertTrue(circuitOutputDto.getListOfTracks().isEmpty())
        );
    }

    @Test
    void shouldReturnListOfCircuitOutputDtoObjects() throws Exception {
//        given
        circuitRepository.save(secondCircuitEntity);
//        when
        String result = mockMvc.perform(get(URL + "?page=" + page + "&size=" + size))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        List<CircuitOutputDto> listOfCircuits = objectMapper.readValue(result, new TypeReference<>() {});
//        then
        assertAll(
                () -> assertNotNull(listOfCircuits),
                () -> assertEquals(2, listOfCircuits.size()),
                () -> assertEquals("Tor Poznan", listOfCircuits.get(0).getName()),
                () -> assertEquals("Najwiekszy tor w Polsce", listOfCircuits.get(0).getDescription()),
                () -> assertEquals("Poznan", listOfCircuits.get(0).getCity()),
                () -> assertEquals("00-000", listOfCircuits.get(0).getPostalCode()),
                () -> assertEquals("Poznanska", listOfCircuits.get(0).getStreet()),
                () -> assertEquals("3", listOfCircuits.get(0).getStreetNumber()),
                () -> assertEquals("tor@poznan.pl", listOfCircuits.get(0).getEmail()),
                () -> assertEquals("123-456-789", listOfCircuits.get(0).getPhoneNumber()),
                () -> assertTrue(listOfCircuits.get(0).getListOfTracks().isEmpty()),
                () -> assertEquals("Tor Jastrzab", listOfCircuits.get(1).getName()),
                () -> assertEquals("Tor w okolicy Radomia", listOfCircuits.get(1).getDescription()),
                () -> assertEquals("Jastrzab", listOfCircuits.get(1).getCity()),
                () -> assertEquals("26-502", listOfCircuits.get(1).getPostalCode()),
                () -> assertEquals("Czerwienica", listOfCircuits.get(1).getStreet()),
                () -> assertEquals("25", listOfCircuits.get(1).getStreetNumber()),
                () -> assertEquals("tor@jastrzab.pl", listOfCircuits.get(1).getEmail()),
                () -> assertEquals("987-654-321", listOfCircuits.get(1).getPhoneNumber()),
                () -> assertTrue(listOfCircuits.get(1).getListOfTracks().isEmpty())
        );
    }

    @Test
    void shouldAddNewCircuitToTheDatabase() throws Exception {
//        given
        String requestBody = objectMapper.writeValueAsString(circuitDto);
//        when
        mockMvc.perform(post(URL)
                .contentType(APPLICATION_JSON)
                .content(requestBody))
                .andDo(print())
                .andExpect(status().isCreated());

        List<CircuitEntity> allCircuits = circuitRepository.findAll();
//        then
        assertAll(
                () -> assertNotNull(allCircuits),
                () -> assertEquals("Silesia Ring", allCircuits.get(1).getName()),
                () -> assertEquals("Tor na slasku", allCircuits.get(1).getDescription()),
                () -> assertEquals("Kamien Slaski", allCircuits.get(1).getCity()),
                () -> assertEquals("47-325", allCircuits.get(1).getPostalCode()),
                () -> assertEquals("Lotnicza", allCircuits.get(1).getStreet()),
                () -> assertEquals("5-7", allCircuits.get(1).getStreetNumber()),
                () -> assertEquals("silesia@ring.pl", allCircuits.get(1).getEmail()),
                () -> assertEquals("691-017-555", allCircuits.get(1).getPhoneNumber())
        );
    }

    @Test
    void shouldEditCircuitEntityById() throws Exception {
//        given
        String requestBody = objectMapper.writeValueAsString(circuitDto);
        CircuitEntity savedCircuit = circuitRepository.save(firstCircuitEntity);
        long id = savedCircuit.getId();
//        when
//        WHEN CIRCUIT NOR FOUND
        mockMvc.perform(patch(URL + "/" + WRONG_ID)
                .contentType(APPLICATION_JSON)
                .content(requestBody))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andReturn();

//        WHEN CIRCUIT WAS FOUND
        mockMvc.perform(patch(URL + "/" + id)
                .contentType(APPLICATION_JSON)
                .content(requestBody))
                .andDo(print())
                .andExpect(status().isNoContent())
                .andReturn();

        CircuitEntity circuit = circuitRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Circuit not found"));
//        then
        assertAll(
                () -> assertNotNull(circuit),
                () -> assertEquals("Silesia Ring", circuit.getName()),
                () -> assertEquals("Tor na slasku", circuit.getDescription()),
                () -> assertEquals("Kamien Slaski", circuit.getCity()),
                () -> assertEquals("47-325", circuit.getPostalCode()),
                () -> assertEquals("Lotnicza", circuit.getStreet()),
                () -> assertEquals("5-7", circuit.getStreetNumber()),
                () -> assertEquals("silesia@ring.pl", circuit.getEmail()),
                () -> assertEquals("691-017-555", circuit.getPhoneNumber())
        );
    }

    @Test
    void shouldDeleteCircuitFromTheDatabase() throws Exception {
//        given
        CircuitEntity circuit = circuitRepository.save(firstCircuitEntity);
        long id = circuit.getId();
//        when
//        WHEN CIRCUIT NOT FOUND
        mockMvc.perform(delete(URL + "/" + WRONG_ID))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andReturn();

//        WHEN CIRCUIT WAS FOUND
        mockMvc.perform(delete(URL + "/" + id))
                .andDo(print())
                .andExpect(status().isNoContent())
                .andReturn();
        List<CircuitEntity> circuits = circuitRepository.findAll();
//        then
        assertFalse(circuits.contains(firstCircuitEntity));
    }

    @Getter
    @Setter
    private static class CircuitOutputDto {

        private long id;
        private String name;
        private String description;
        private String city;
        private String postalCode;
        private String street;
        private String streetNumber;
        private String email;
        private String phoneNumber;
        private List<TrackSimpleEntity> listOfTracks;
    }
}