package pl.grzegorz.lapservice.lap;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.test.web.servlet.MockMvc;
import pl.grzegorz.lapservice.BaselineIntegrationTest;
import pl.grzegorz.lapservice.biker.BikerFeignFacade;
import pl.grzegorz.lapservice.circuit.CircuitFeignFacade;
import pl.grzegorz.lapservice.lap.dto.input.DateDto;
import pl.grzegorz.lapservice.lap.dto.input.DateLapDto;
import pl.grzegorz.lapservice.lap.dto.input.LapDto;
import pl.grzegorz.lapservice.lap.dto.output.LapDetailsByBiker;
import pl.grzegorz.lapservice.lap.dto.output.LapDetailsByTrackOutputDto;
import pl.grzegorz.lapservice.motorcycle.MotorcycleFeignFacade;

import java.util.List;

import static java.time.LocalDate.of;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static pl.grzegorz.lapservice.lap.LapInitTestValue.*;

class LapControllerTest extends BaselineIntegrationTest {

    private static final String URL = "/laps";
    private static final String PAGE = "1";
    private static final String SIZE = "2";
    private static final long BIKER_ID = getLapDocument().getBikerDetails().getId();
    private static final long CIRCUIT_ID = getLapDocument().getCircuitDetails().getCircuitId();
    private static final long TRACK_ID = getLapDocument().getCircuitDetails().getTrack().getTrackId();
    private static final long MOTORCYCLE_ID = getLapDocument().getMotorcycleDetails().getMotorcycleId();


    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private LapRepository lapRepository;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private WireMockServer wireMockServer;

    private static LapDocument lapDocument;
    private static DateDto dateDto;
    private static DateLapDto dateLapDto;
    private static LapDto lapDto;

    @BeforeAll
    static void start() {
        lapDocument = getLapDocument();
        dateDto = getDateDto();
        dateLapDto = getDateLapDto();
        lapDto = getLapDto();
    }

    @BeforeEach
    void setupDb() {
        lapRepository.save(lapDocument);
    }

    @AfterEach
    void clearDb() {
        lapRepository.deleteAll();
    }

    @Test
    void shouldReturnListOfAllLapsByBikerId() throws Exception {
//        given
//        when
        String mockResult = mockMvc.perform(get(URL + "/" + BIKER_ID + "/bikers")
                        .param("page", PAGE)
                        .param("size", SIZE))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        List<LapDetailsByBiker> lapsByBiker = objectMapper.readValue(mockResult, new TypeReference<>() {
        });
//        then
        assertAll(
                () -> assertNotNull(lapsByBiker),
                () -> assertEquals(1, lapsByBiker.size()),
                () -> assertEquals("1:22:345", lapsByBiker.get(0).getLapTime()),
                () -> assertEquals(of(2022, 3, 21), lapsByBiker.get(0).getLapDate()),
                () -> assertEquals("BMW", lapsByBiker.get(0).getMotorcycleDetails().getBrand()),
                () -> assertEquals("S1000RR", lapsByBiker.get(0).getMotorcycleDetails().getModel()),
                () -> assertEquals(999, lapsByBiker.get(0).getMotorcycleDetails().getCapacity()),
                () -> assertEquals(215, lapsByBiker.get(0).getMotorcycleDetails().getHorsePower()),
                () -> assertEquals(1L, lapsByBiker.get(0).getMotorcycleDetails().getBikerId()),
                () -> assertEquals(5L, lapsByBiker.get(0).getCircuitDetails().getCircuitId()),
                () -> assertEquals("Masaryk Circuit", lapsByBiker.get(0).getCircuitDetails().getCircuitName()),
                () -> assertEquals(22L, lapsByBiker.get(0).getCircuitDetails().getTrack().getTrackId()),
                () -> assertEquals("Main track", lapsByBiker.get(0).getCircuitDetails().getTrack().getTrackName()),
                () -> assertEquals(7652.2, lapsByBiker.get(0).getCircuitDetails().getTrack().getTrackLength())
        );
    }

    @Test
    void shouldReturnListOfLapDocumentObjectByDateRangeAndCircuitAndBiker() throws Exception {
//        given
        String requestBody = objectMapper.writeValueAsString(dateDto);
//        when
        String mockResult = mockMvc.perform(get(URL + "/" + BIKER_ID + "/bikers/" + CIRCUIT_ID + "/circuits")
                        .param("page", PAGE)
                        .param("size", SIZE)
                        .contentType(APPLICATION_JSON)
                        .content(requestBody))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        List<LapDetailsByBiker> lapsByBiker = objectMapper.readValue(mockResult, new TypeReference<>() {
        });
//        then
        assertAll(
                () -> assertNotNull(lapsByBiker),
                () -> assertEquals(1, lapsByBiker.size()),
                () -> assertEquals("1:22:345", lapsByBiker.get(0).getLapTime()),
                () -> assertEquals(of(2022, 3, 21), lapsByBiker.get(0).getLapDate()),
                () -> assertEquals("BMW", lapsByBiker.get(0).getMotorcycleDetails().getBrand()),
                () -> assertEquals("S1000RR", lapsByBiker.get(0).getMotorcycleDetails().getModel()),
                () -> assertEquals(999, lapsByBiker.get(0).getMotorcycleDetails().getCapacity()),
                () -> assertEquals(215, lapsByBiker.get(0).getMotorcycleDetails().getHorsePower()),
                () -> assertEquals(1L, lapsByBiker.get(0).getMotorcycleDetails().getBikerId()),
                () -> assertEquals(5L, lapsByBiker.get(0).getCircuitDetails().getCircuitId()),
                () -> assertEquals("Masaryk Circuit", lapsByBiker.get(0).getCircuitDetails().getCircuitName()),
                () -> assertEquals(22L, lapsByBiker.get(0).getCircuitDetails().getTrack().getTrackId()),
                () -> assertEquals("Main track", lapsByBiker.get(0).getCircuitDetails().getTrack().getTrackName()),
                () -> assertEquals(7652.2, lapsByBiker.get(0).getCircuitDetails().getTrack().getTrackLength())
        );
    }

    @Test
    void shouldReturnListOfLapsByTrackAndDateRangeAndCapacityRange() throws Exception {
//        given
        String requestBody = objectMapper.writeValueAsString(dateLapDto);
//        when
        String mockResult = mockMvc.perform(get(URL + "/" + TRACK_ID + "/tracks")
                        .param("page", PAGE)
                        .param("size", SIZE)
                        .contentType(APPLICATION_JSON)
                        .content(requestBody))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        LapDetailsByTrackOutputDto result = objectMapper.readValue(mockResult, LapDetailsByTrackOutputDto.class);
//        then
        assertAll(
                () -> assertNotNull(result),
                () -> assertEquals("Masaryk Circuit", result.getCircuitName()),
                () -> assertEquals("Main track", result.getTrackName()),
                () -> assertEquals(7652.2, result.getTrackLength()),
                () -> assertEquals(of(2022,3,20), result.getStartDate()),
                () -> assertEquals(of(2022,3,22), result.getEndDate()),
                () -> assertEquals(1, result.getBikers().size()),
                () -> assertEquals(1, result.getBikers().get(0).getBikerId()),
                () -> assertEquals("Tomasz", result.getBikers().get(0).getFirstName()),
                () -> assertEquals("Tomaszewski", result.getBikers().get(0).getLastName()),
                () -> assertEquals("tomasz_123", result.getBikers().get(0).getUsername()),
                () -> assertEquals("1:22:345", result.getBikers().get(0).getLapTime()),
                () -> assertEquals(1, result.getBikers().get(0).getMotorcycle().getMotorcycleId()),
                () -> assertEquals("BMW", result.getBikers().get(0).getMotorcycle().getBrand()),
                () -> assertEquals("S1000RR", result.getBikers().get(0).getMotorcycle().getModel()),
                () -> assertEquals(999, result.getBikers().get(0).getMotorcycle().getCapacity()),
                () -> assertEquals(215, result.getBikers().get(0).getMotorcycle().getHorsePower())
        );
    }

    @Test
    void shouldAddNewLapToTheDatabase() throws Exception {
//        given
        lapRepository.deleteAll();
        String requestBody = objectMapper.writeValueAsString(lapDto);
//        when
        mockMvc.perform(post(URL + "/" + TRACK_ID + "/tracks/" + BIKER_ID + "/bikers/" +  MOTORCYCLE_ID + "/motorcycles")
                .contentType(APPLICATION_JSON)
                .content(requestBody))
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn();

        List<LapDocument> laps = lapRepository.findAll();
//        then
        assertAll(
                () -> assertNotNull(laps),
                () -> assertEquals(1, laps.size())
        );
    }
}