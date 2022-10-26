package pl.grzegorz.motorcycleservice.motorcycle;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import pl.grzegorz.motorcycleservice.BaseIntegrationTest;
import pl.grzegorz.motorcycleservice.motorcycle_class.query.MotorcycleClassSimpleEntity;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static pl.grzegorz.motorcycleservice.motorcycle.UnitTestMotorcycleInitValue.getMotorcycleEntity;

class MotorcycleControllerTest extends BaseIntegrationTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private MotorcycleRepository motorcycleRepository;

    private static final String URL = "/motorcycles";

    private static ObjectMapper objectMapper;
    private static MotorcycleEntity motorcycle;

    @BeforeAll
    static void setUp() {
        objectMapper = new ObjectMapper();
        motorcycle = getMotorcycleEntity();
    }

    @AfterEach
    void tearDown() {
        motorcycleRepository.deleteAll();
    }

    @Test
    void secondTest() throws Exception {
        long id = 1;
        motorcycleRepository.save(motorcycle);

        String result = mockMvc.perform(get(URL + "/" + id))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        MotorcycleEntityIntegrationTest testResult = objectMapper.readValue(result, MotorcycleEntityIntegrationTest.class);

        assertAll(
                () -> assertNotNull(testResult)
        );
    }

    @Getter
    @Setter
    private static class MotorcycleEntityIntegrationTest {

        private long id;
        private String brand;
        private String model;
        private int capacity;
        private int horsePower;
        private int vintage;
        private MotorcycleClassSimpleEntity motorcycleClass;
        private String bikerId;

    }

}