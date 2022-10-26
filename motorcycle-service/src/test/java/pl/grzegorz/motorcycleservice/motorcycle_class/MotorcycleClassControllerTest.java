package pl.grzegorz.motorcycleservice.motorcycle_class;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import pl.grzegorz.motorcycleservice.BaseIntegrationTest;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static pl.grzegorz.motorcycleservice.motorcycle_class.MotorcycleClassTestInitValue.getUnitTestingMotorcycleClassEntity;

class MotorcycleClassControllerTest extends BaseIntegrationTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private MotorcycleClassRepository motorcycleClassRepository;

    private static ObjectMapper objectMapper;
    private static MotorcycleClassEntity motorcycleClass;

    private final String URL = "/motorcycle-classes";

    @BeforeAll
    static void setup() {
        objectMapper = new ObjectMapper();
        motorcycleClass = getUnitTestingMotorcycleClassEntity();
    }

    @AfterEach
    void tearDown() {
        motorcycleClassRepository.deleteAll();
    }

    @Test
    void firstTest() throws Exception {
        long id = 1;
        motorcycleClassRepository.save(motorcycleClass);

        String result = mockMvc.perform(get(URL + "/" + id))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        MotorcycleClassEntityIntegrationTest testResult = objectMapper.readValue(result, MotorcycleClassEntityIntegrationTest.class);

        assertAll(
                () -> assertNotNull(testResult)
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