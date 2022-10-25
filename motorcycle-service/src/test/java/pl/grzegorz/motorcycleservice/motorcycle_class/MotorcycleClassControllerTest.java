package pl.grzegorz.motorcycleservice.motorcycle_class;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import pl.grzegorz.motorcycleservice.BaseIntegrationTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static pl.grzegorz.motorcycleservice.motorcycle_class.MotorcycleClassTestInitValue.getUnitTestingMotorcycleClassEntity;

@Transactional
class MotorcycleClassControllerTest extends BaseIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MotorcycleClassRepository motorcycleClassRepository;

    private ObjectMapper objectMapper;
    private MotorcycleClassEntity motorcycleClass;

    private final String URL = "/motorcycle-classes";

    @BeforeEach
    void setup() {
        objectMapper = new ObjectMapper();
        motorcycleClass = getUnitTestingMotorcycleClassEntity();
        motorcycleClassRepository.save(motorcycleClass);
    }

    @AfterEach
    void clean() {
        motorcycleClassRepository.deleteAll();
    }

    @Test
    void shouldReturnMotorcycleClassOutputDtoObjectWithFirstId() throws Exception {
//        given
        long id = 1;
//        when
        String result = mockMvc.perform(get(URL + "/" + id))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
//        when
        MotorcycleOutputDtoForIntegrationTest motorcycleClass =
                objectMapper.readValue(result, MotorcycleOutputDtoForIntegrationTest.class);
//        then
        assertAll(
                () -> assertNotNull(motorcycleClass),
                () -> assertEquals(1, motorcycleClass.getId()),
                () -> assertEquals("Power Cruiser", motorcycleClass.getName()),
                () -> assertEquals("Powerful and heavy motorcycle", motorcycleClass.getDescription())
        );
    }

    @Getter
    @Setter
    static class MotorcycleOutputDtoForIntegrationTest {
        private long id;
        private String name;
        private String description;
    }

}