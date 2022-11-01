package pl.grzegorz.bikerservice.biker;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;
import pl.grzegorz.bikerservice.BaselineIntegrationTest;
import pl.grzegorz.bikerservice.biker.dto.BikerDto;

import static pl.grzegorz.bikerservice.biker.BikerInitTestValue.*;

class BikerControllerTest extends BaselineIntegrationTest {

    private static final String URL = "/bikers";
    private static final long WRONG_ID = 100;

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private BikerRepository bikerRepository;
    @Autowired
    private ObjectMapper objectMapper;

    private static BikerEntity firstBiker;
    private static BikerEntity secondBiker;
    private static BikerDto bikerDto;

    @BeforeAll
    static void start() {
        firstBiker = getFirstBikerEntity();
        secondBiker = getSecondBikerEntity();
        bikerDto = getBikerDto();
    }

    @BeforeEach
    void setupDb() {
        bikerRepository.save(firstBiker);
    }

    @AfterEach
    void clearDb() {
        bikerRepository.deleteAll();
    }

    @Test
    void shouldReturnSingleBiker() {
        BikerEntity biker = bikerRepository.save(firstBiker)
    }

}