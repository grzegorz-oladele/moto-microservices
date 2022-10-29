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
import pl.grzegorz.motorcycleservice.BaseIntegrationTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static pl.grzegorz.motorcycleservice.bike_class.MotorcycleClassTestInitValue.getFirstMotorcycleClassEntity;
import static pl.grzegorz.motorcycleservice.bike_class.MotorcycleClassTestInitValue.getSecondMotorcycleClassEntity;

class BikeClassControllerTest extends BaseIntegrationTest {

    private static final String URL = "/motorcycle-classes";

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private BikeClassRepository bikeClassRepository;

    private static ObjectMapper objectMapper;
    private static BikeClassEntity firstBikeClass;
    private static BikeClassEntity secondBikeClass;

    @BeforeAll
    static void start() {
        objectMapper = new ObjectMapper();
        firstBikeClass = getFirstMotorcycleClassEntity();
        secondBikeClass = getSecondMotorcycleClassEntity();
    }

    @BeforeEach
    void setupDb() {
        bikeClassRepository.save(firstBikeClass);
    }

    @AfterEach
    void clearDb() {
        bikeClassRepository.delete(firstBikeClass);
    }

    @Test
    void firstTest() throws Exception {
       long id = firstBikeClass.getId();
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
        clearDb();
    }

    @Test
    void shouldReturnListWithTwoMotorcycleClasses() throws Exception {
        bikeClassRepository.save(secondBikeClass);
        String result = mockMvc.perform(get(URL))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        List<MotorcycleClassEntityIntegrationTest> testResult = objectMapper.readValue(result, new TypeReference<>() {});

        assertAll(
                () -> assertNotNull(testResult),
                () -> assertEquals(2, testResult.size()),
                () -> assertEquals("Power Cruiser", testResult.get(0).getName()),
                () -> assertEquals("Powerful and heavy motorcycle", testResult.get(0).getDescription()),
                () -> assertEquals("Supersport", testResult.get(1).getName()),
                () -> assertEquals("Optimized for speed, accelerating and braking", testResult.get(1).getDescription())
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