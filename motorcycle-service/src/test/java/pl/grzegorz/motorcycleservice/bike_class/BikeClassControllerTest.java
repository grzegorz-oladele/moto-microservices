package pl.grzegorz.motorcycleservice.bike_class;

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
import pl.grzegorz.motorcycleservice.BaselineIntegrationTest;
import pl.grzegorz.motorcycleservice.bike_class.dto.input.BikeClassDto;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static pl.grzegorz.motorcycleservice.bike_class.BikeClassTestInitValue.*;

class BikeClassControllerTest extends BaselineIntegrationTest {

    private static final String URL = "/motorcycle-classes";
    private static final long WRONG_ID = 100;

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private BikeClassRepository bikeClassRepository;
    @Autowired
    private ObjectMapper objectMapper;

    private static BikeClassEntity firstBikeClass;
    private static BikeClassEntity secondBikeClass;
    private static BikeClassDto bikeClassDto;

    @BeforeAll
    static void start() {
        firstBikeClass = getFirstMotorcycleClassEntity();
        secondBikeClass = getSecondMotorcycleClassEntity();
        bikeClassDto = getUnitTestingMotorcycleClassDto();
    }

    @BeforeEach
    void setupDb() {
        bikeClassRepository.save(firstBikeClass);
    }

    @AfterEach
    void clearDb() {
        bikeClassRepository.deleteAll();
    }

    @Test
    void shouldReturnSingleBikeClass() throws Exception {
        BikeClassEntity bikeClass = bikeClassRepository.save(firstBikeClass);
        long id = bikeClass.getId();

//        When motorcycle class not found
        mockMvc.perform(get(URL + "/" + WRONG_ID))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andReturn()
                .getResponse()
                .getContentAsString();

//        When motorcycle class was found
        String result = mockMvc.perform(get(URL + "/" + id))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        MotorcycleClassEntityIntegrationTest testResult = objectMapper.readValue(result, MotorcycleClassEntityIntegrationTest.class);

        assertAll(
                () -> assertNotNull(testResult),
                () -> assertEquals("Power Cruiser", testResult.name),
                () -> assertEquals("Powerful and heavy motorcycle", testResult.getDescription())
        );
    }

    @Test
    void shouldReturnListWithTwoMotorcycleClasses() throws Exception {
//        given
        String page = "1";
        String size = "2";
        bikeClassRepository.save(secondBikeClass);
//        when
        String result = mockMvc.perform(get(URL)
                        .param("page", page)
                        .param("size", size))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        List<MotorcycleClassEntityIntegrationTest> testResult = objectMapper.readValue(result, new TypeReference<>() {
        });
//        then
        assertAll(
                () -> assertNotNull(testResult),
                () -> assertEquals(2, testResult.size()),
                () -> assertEquals("Power Cruiser", testResult.get(0).getName()),
                () -> assertEquals("Powerful and heavy motorcycle", testResult.get(0).getDescription()),
                () -> assertEquals("Supersport", testResult.get(1).getName()),
                () -> assertEquals("Optimized for speed, accelerating and braking", testResult.get(1).getDescription())
        );
    }

    @Test
    void shouldAddNewBikeClassToTheDatabase() throws Exception {
//        given
        String requestBody = objectMapper.writeValueAsString(bikeClassDto);
//        when
        mockMvc.perform(post(URL)
                        .contentType(APPLICATION_JSON)
                        .content(requestBody)
                )
                .andDo(print())
                .andExpect(status().is(201))
                .andReturn();

        List<BikeClassEntity> bikeClassList = bikeClassRepository.findAll();
//        then
        assertAll(
                () -> assertNotNull(bikeClassList),
                () -> assertEquals(2, bikeClassList.size()),
                () -> assertEquals("Power Cruiser", bikeClassList.get(0).getName()),
                () -> assertEquals("Powerful and heavy motorcycle", bikeClassList.get(0).getDescription()),
                () -> assertEquals("Cafe Racer", bikeClassList.get(1).getName()),
                () -> assertEquals("Small-capacity motorcycles for getting around town fast",
                        bikeClassList.get(1).getDescription())
        );
    }

    @Test
    void shouldEditBikeClassEntity() throws Exception {
//        given
        BikeClassEntity bikeClassEntity = bikeClassRepository.save(firstBikeClass);
        String requestBody = objectMapper.writeValueAsString(bikeClassDto);
//        when

//        When motorcycle class not found
        mockMvc.perform(patch(URL + "/" + WRONG_ID)
                        .contentType(APPLICATION_JSON)
                        .content(requestBody))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andReturn();

//        When motorcycle class was found
        mockMvc.perform(patch(URL + "/" + bikeClassEntity.getId())
                        .contentType(APPLICATION_JSON)
                        .content(requestBody))
                .andDo(print())
                .andExpect(status().isNoContent())
                .andReturn();

        BikeClassEntity bikeClass = bikeClassRepository.findById(bikeClassEntity.getId())
                .orElseThrow(() -> new IllegalArgumentException("Bike class not found"));
//        then
        assertAll(
                () -> assertNotNull(bikeClass),
                () -> assertEquals("Cafe Racer", bikeClass.getName()),
                () -> assertEquals("Small-capacity motorcycles for getting around town fast",
                        bikeClass.getDescription())
        );
    }

    @Test
    void shouldRemoveBikeClass() throws Exception {
//        given
        BikeClassEntity bikeClass = bikeClassRepository.save(secondBikeClass);
//        when

//        When motorcycle class not found
        mockMvc.perform(delete(URL + "/" + WRONG_ID))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andReturn();

//        When motorcycle class was found
        mockMvc.perform(delete(URL + "/" + bikeClass.getId()))
                .andDo(print())
                .andExpect(status().isNoContent())
                .andReturn();

        List<BikeClassEntity> bikeClassList = bikeClassRepository.findAll();
//        then
        assertAll(
                () -> assertNotNull(bikeClassList),
                () -> assertFalse(bikeClassList.contains(bikeClass))
        );
    }

    @Getter
    @Setter
    private static class MotorcycleClassEntityIntegrationTest {

        private long id;
        private String name;
        private String description;
    }
}