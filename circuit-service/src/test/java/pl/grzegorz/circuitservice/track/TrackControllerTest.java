package pl.grzegorz.circuitservice.track;

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
import pl.grzegorz.circuitservice.circuit.CircuitFacade;
import pl.grzegorz.circuitservice.circuit.dto.input.CircuitDto;
import pl.grzegorz.circuitservice.circuit.query.CircuitSimpleEntity;
import pl.grzegorz.circuitservice.track.dto.input.TrackDto;

import java.util.List;

import static java.lang.Boolean.TRUE;
import static lombok.AccessLevel.PRIVATE;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static pl.grzegorz.circuitservice.circuit.CircuitTestInitValue.getCircuitDto;
import static pl.grzegorz.circuitservice.track.TrackTestInitValue.*;

class TrackControllerTest extends BaselineIntegrationTest {

    private static final String URL = "/tracks";
    private static final String CIRCUITS = "/circuits";
    private static final long WRONG_ID = 100;
    private static final int page = 1;
    private static final long size = 2;

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private TrackRepository trackRepository;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private CircuitFacade circuitFacade;

    private static TrackEntity firstTrack;
    private static TrackEntity secondTrack;
    private static CircuitDto circuitDto;
    private static TrackDto trackDto;

    @BeforeAll
    static void start() {
        firstTrack = getTrackEntity();
        secondTrack = getSecondTrackEntity();
        circuitDto = getCircuitDto();
        trackDto = getTrackDto();
    }

    @BeforeEach
    void setUp() {
        circuitFacade.addNewCircuit(circuitDto);
        trackRepository.save(firstTrack);
    }

    @AfterEach
    void clearDb() {
        trackRepository.deleteAll();
    }

    @Test
    void shouldReturnSingleObjectOfTrackOutputDto() throws Exception {
//        given
        TrackEntity savedTrack = trackRepository.save(firstTrack);
        long id = savedTrack.getId();
//        when
//        WHEN TRACK NOT FOUND
        mockMvc.perform(get(URL + "/" + WRONG_ID))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andReturn();

//        WHEN TRACK WAS FOUND
        String result = mockMvc.perform(get(URL + "/" + id))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        TrackOutputDto track = objectMapper.readValue(result, TrackOutputDto.class);
//        then
        assertAll(
                () -> assertNotNull(track),
                () -> assertEquals("Tor nr 1", track.getName()),
                () -> assertFalse(track.getIsThereFence()),
                () -> assertTrue(track.getIsTherePlaceForAd()),
                () -> assertEquals(2937.0, track.getTrackLength())
        );
    }

    @Test
    void shouldReturnListOfTrackOutputDtoObjects() throws Exception {
//        given
        trackRepository.save(secondTrack);
//        when
        String result = mockMvc.perform(get(URL + "?page=" + page + "&size=" + size))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        List<TrackOutputDto> listOfTracks = objectMapper.readValue(result, new TypeReference<>() {
        });
//        then
        assertAll(
                () -> assertNotNull(listOfTracks),
                () -> assertEquals(2, listOfTracks.size()),
                () -> assertEquals("Tor nr 1", listOfTracks.get(0).getName()),
                () -> assertFalse(listOfTracks.get(0).getIsThereFence()),
                () -> assertTrue(listOfTracks.get(0).getIsTherePlaceForAd()),
                () -> assertEquals(2937.0, listOfTracks.get(0).getTrackLength()),
                () -> assertEquals("Tor nr 2", listOfTracks.get(1).getName()),
                () -> assertTrue(listOfTracks.get(1).getIsThereFence()),
                () -> assertFalse(listOfTracks.get(1).getIsTherePlaceForAd()),
                () -> assertEquals(1827.0, listOfTracks.get(1).getTrackLength())
        );
    }

    @Test
    void shouldReturnListOfTrackOutputDtoObjectsByCircuit() throws Exception {
//        given
        long circuitId = 1;
        trackRepository.save(secondTrack);
//        when
//        WHEN CIRCUIT NOT FOUND
        String wrongResult = mockMvc.perform(get(URL + "/" + WRONG_ID + CIRCUITS + "?page=" + page +
                        "&size= " + size))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        List<TrackOutputDto> emptyList = objectMapper.readValue(wrongResult, new TypeReference<>() {
        });

//        WHEN CIRCUIT WAS FOUND
        String result = mockMvc.perform(get(URL + "/" + circuitId + CIRCUITS + "?page=" + page +
                        "&size=" + size))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        List<TrackOutputDto> listOfTracks = objectMapper.readValue(result, new TypeReference<>() {
        });
//        then
        assertAll(
                () -> assertNotNull(emptyList),
                () -> assertTrue(emptyList.isEmpty()),
                () -> assertNotNull(listOfTracks),
                () -> assertEquals(2, listOfTracks.size()),
                () -> assertEquals("Tor nr 1", listOfTracks.get(0).getName()),
                () -> assertEquals(2937.0, listOfTracks.get(0).getTrackLength()),
                () -> assertEquals("Tor nr 2", listOfTracks.get(1).getName()),
                () -> assertEquals(1827.0, listOfTracks.get(1).getTrackLength())
        );
    }

    @Test
    void shouldAddNewTrackToTheDatabase() throws Exception {
//        given
        long circuitId = 1;
        trackRepository.deleteAll();
        String requestBody = objectMapper.writeValueAsString(trackDto);
//        when
//        WHEN CIRCUIT NOT FOUND
        mockMvc.perform(post(URL + "/" + WRONG_ID + CIRCUITS)
                        .contentType(APPLICATION_JSON)
                        .content(requestBody))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andReturn();

//        WHEN CIRCUIT WAS FOUND
        mockMvc.perform(post(URL + "/" + circuitId + CIRCUITS)
                        .contentType(APPLICATION_JSON)
                        .content(requestBody))
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn();
        List<TrackEntity> allTracks = trackRepository.findAll();
//        then
        assertAll(
                () -> assertNotNull(allTracks),
                () -> assertEquals("Tor nr 4", allTracks.get(0).getName()),
                () -> assertEquals(3472.0, allTracks.get(0).getTrackLength()),
                () -> assertEquals(TRUE, allTracks.get(0).getIsThereFence()),
                () -> assertEquals(TRUE, allTracks.get(0).getIsTherePlaceForAd())
        );
    }

    @Test
    void shouldEditTrackEntityById() throws Exception {
//        given
        TrackEntity savedTrack = trackRepository.save(firstTrack);
        long id = savedTrack.getId();
        String requestBody = objectMapper.writeValueAsString(trackDto);
//        when
//        WHEN TRACK NOT FOUND
        mockMvc.perform(patch(URL + "/" + WRONG_ID)
                        .contentType(APPLICATION_JSON)
                        .content(requestBody))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andReturn();

//        WHEN TRACK WAS FOUND
        mockMvc.perform(patch(URL + "/" + id)
                .contentType(APPLICATION_JSON)
                .content(requestBody))
                .andDo(print())
                .andExpect(status().isNoContent())
                .andReturn();

        TrackEntity track = trackRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Track not found"));
//        then
        assertAll(
                () -> assertNotNull(track),
                () -> assertEquals("Tor nr 4", track.getName()),
                () -> assertEquals(3472.0, track.getTrackLength()),
                () -> assertEquals(TRUE, track.getIsThereFence()),
                () -> assertEquals(TRUE, track.getIsTherePlaceForAd())
        );
    }

    @Test
    void shouldDeleteTrackFromTgeDatabase() throws Exception {
//        given
        TrackEntity savedTrack = trackRepository.save(firstTrack);
        long id = savedTrack.getId();
//        when
//        WHEN TRACK NOT FOUND
        mockMvc.perform(delete(URL + "/" + WRONG_ID))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andReturn();

//        WHEN TRACK WAS FOUND
        mockMvc.perform(delete(URL + "/" + id))
                .andDo(print())
                .andExpect(status().isNoContent())
                .andReturn();
        List<TrackEntity> allTracks = trackRepository.findAll();
//        then
        assertFalse(allTracks.contains(firstTrack));
    }

    @Getter(value = PRIVATE)
    @Setter(value = PRIVATE)
    private static class TrackOutputDto {

        private long id;
        private String name;
        private double trackLength;
        private Boolean isThereFence;
        private Boolean isTherePlaceForAd;
        private CircuitSimpleEntity circuitSimple;
    }
}