package pl.grzegorz.motorcycleservice.motorcycle;

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
import pl.grzegorz.motorcycleservice.bike_class.BikeClassFacade;
import pl.grzegorz.motorcycleservice.bike_class.dto.input.BikeClassDto;
import pl.grzegorz.motorcycleservice.bike_class.dto.output.BikeClassOutputDto;
import pl.grzegorz.motorcycleservice.bike_class.query.BikeClassSimpleEntity;
import pl.grzegorz.motorcycleservice.motorcycle.dto.input.MotorcycleDto;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static pl.grzegorz.motorcycleservice.bike_class.BikeClassTestInitValue.getUnitTestingMotorcycleClassDto;
import static pl.grzegorz.motorcycleservice.motorcycle.UnitTestMotorcycleInitValue.*;

class MotorcycleControllerTest extends BaselineIntegrationTest {

    private static final String URL = "/motorcycles";
    private static final long WRONG_ID = 100;
    private static final long WRONG_BIKE_CLASS_ID = 200;

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private MotorcycleRepository motorcycleRepository;
    @Autowired
    private BikeClassFacade bikeClassFacade;
    @Autowired
    private ObjectMapper objectMapper;

    private static MotorcycleEntity motorcycle;
    private static MotorcycleEntity secondMotorcycle;
    private static MotorcycleDto motorcycleDto;
    private static BikeClassDto bikeClassDto;

    @BeforeAll
    static void start() {
        motorcycle = getMotorcycleEntity();
        secondMotorcycle = getSecondMotorcycleEntity();
        motorcycleDto = getMotorcycleDto();
        bikeClassDto = getUnitTestingMotorcycleClassDto();
    }

    @BeforeEach
    void setupDb() {
        motorcycleRepository.save(motorcycle);
    }

    @AfterEach
    void clearDb() {
        motorcycleRepository.deleteAll();
    }

    @Test
    void shouldReturnSingleMotorcycle() throws Exception {
//        given
        MotorcycleEntity motorcycle = motorcycleRepository.save(secondMotorcycle);
        long id = motorcycle.getId();
//        when

//        When motorcycle not found
        mockMvc.perform(get(URL + "/" + WRONG_ID))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andReturn();

//        When motorcycle was found
        String result = mockMvc.perform(get(URL + "/" + id))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        MotorcycleEntityIntegrationTest testResult = objectMapper.readValue(result, MotorcycleEntityIntegrationTest.class);

        assertAll(
                () -> assertNotNull(testResult),
                () -> assertEquals("BMW", testResult.getBrand()),
                () -> assertEquals("S1000RR", testResult.getModel()),
                () -> assertEquals(215, testResult.getHorsePower()),
                () -> assertEquals(2022, testResult.getVintage()),
                () -> assertEquals(999, testResult.getCapacity())
        );
    }

    @Test
    void shouldReturnListWithTwoMotorcycles() throws Exception {
//        given
        String page = "1";
        String size = "2";
        motorcycleRepository.save(secondMotorcycle);
//        when
        String result = mockMvc.perform(get(URL)
                        .param("page", page)
                        .param("size", size))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        List<MotorcycleEntityIntegrationTest> testResult = objectMapper.readValue(result, new TypeReference<>() {
        });
//        then
        assertAll(
                () -> assertNotNull(testResult),
                () -> assertEquals(2, testResult.size()),
                () -> assertEquals("Ducati", testResult.get(0).getBrand()),
                () -> assertEquals("Panigale V4S", testResult.get(0).getModel()),
                () -> assertEquals(221, testResult.get(0).getHorsePower()),
                () -> assertEquals(2022, testResult.get(0).getVintage()),
                () -> assertEquals(1098, testResult.get(0).getCapacity()),
                () -> assertEquals("BMW", testResult.get(1).getBrand()),
                () -> assertEquals("S1000RR", testResult.get(1).getModel()),
                () -> assertEquals(215, testResult.get(1).getHorsePower()),
                () -> assertEquals(2022, testResult.get(1).getVintage()),
                () -> assertEquals(999, testResult.get(1).getCapacity())
        );
    }

    @Test
    void shouldAddNewMotorcycleToTheDatabase() throws Exception {
//        given
        bikeClassFacade.addMotorcyclesClass(bikeClassDto);
        List<BikeClassOutputDto> motorcycleClassList = bikeClassFacade.getMotorcycleClassList(1, 1);
        long bikeClassId = motorcycleClassList.get(0).getId();
        String requestBody = objectMapper.writeValueAsString(motorcycleDto);
        String suffixPath = "/bike-classes";
//        when

//        When motorcycle class not found
        mockMvc.perform(post(URL + "/" + WRONG_BIKE_CLASS_ID + suffixPath)
                        .contentType(APPLICATION_JSON)
                        .content(requestBody))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andReturn();

//        When motorcycle class was found
        mockMvc.perform(post(URL + "/" + bikeClassId + suffixPath)
                        .contentType(APPLICATION_JSON)
                        .content(requestBody))
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn();

        List<MotorcycleEntity> motorcycleList = motorcycleRepository.findAll();
//        then
        assertAll(
                () -> assertNotNull(motorcycleList),
                () -> assertEquals(2, motorcycleList.size()),
                () -> assertEquals("Ducati", motorcycleList.get(0).getBrand()),
                () -> assertEquals("Panigale V4S", motorcycleList.get(0).getModel()),
                () -> assertEquals(221, motorcycleList.get(0).getHorsePower()),
                () -> assertEquals(2022, motorcycleList.get(0).getVintage()),
                () -> assertEquals(1098, motorcycleList.get(0).getCapacity()),
                () -> assertEquals("Yamaha", motorcycleList.get(1).getBrand()),
                () -> assertEquals("YZF R1", motorcycleList.get(1).getModel()),
                () -> assertEquals(205, motorcycleList.get(1).getHorsePower()),
                () -> assertEquals(2021, motorcycleList.get(1).getVintage()),
                () -> assertEquals(998, motorcycleList.get(1).getCapacity())
        );
    }

    @Test
    void shouldEditMotorcycleEntity() throws Exception {
//        given
        MotorcycleEntity savedMotorcycle = motorcycleRepository.save(secondMotorcycle);
        long id = savedMotorcycle.getId();
        String requestBody = objectMapper.writeValueAsString(motorcycleDto);
//        when

//        When motorcycle not found
        mockMvc.perform(patch(URL + "/" + WRONG_ID)
                .contentType(APPLICATION_JSON)
                .content(requestBody))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andReturn();

//        When motorcycle was found
        mockMvc.perform(patch(URL + "/" + id)
                .contentType(APPLICATION_JSON)
                .content(requestBody))
                .andDo(print())
                .andExpect(status().isNoContent())
                .andReturn();

        MotorcycleEntity motorcycleEntity = motorcycleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Motorcycle not found"));
//        then
        assertAll(
                () -> assertNotNull(motorcycleEntity),
                () -> assertEquals(id, motorcycleEntity.getId()),
                () -> assertEquals("Yamaha", motorcycleEntity.getBrand()),
                () -> assertEquals("YZF R1", motorcycleEntity.getModel()),
                () -> assertEquals(205, motorcycleEntity.getHorsePower()),
                () -> assertEquals(2021, motorcycleEntity.getVintage()),
                () -> assertEquals(998, motorcycleEntity.getCapacity())
        );
    }

    @Test
    void shouldRemoveMotorcycle() throws Exception {
//        given
        MotorcycleEntity motorcycleEntity = motorcycleRepository.save(secondMotorcycle);
        long motorcycleId = motorcycleEntity.getId();
//        when

//        When motorcycle not found
        mockMvc.perform(delete(URL + "/" + WRONG_ID))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andReturn();

//        When motorcycle was found
        mockMvc.perform(delete(URL + "/" + motorcycleId))
                .andDo(print())
                .andExpect(status().isNoContent())
                .andReturn();

        List<MotorcycleEntity> motorcycleList = motorcycleRepository.findAll();
//        then
        assertAll(
                () -> assertNotNull(motorcycleList),
                () -> assertFalse(motorcycleList.contains(motorcycleEntity))
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
        private BikeClassSimpleEntity motorcycleClass;
        private String bikerId;
    }
}