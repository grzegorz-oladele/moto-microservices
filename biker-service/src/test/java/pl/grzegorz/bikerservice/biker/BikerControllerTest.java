package pl.grzegorz.bikerservice.biker;

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
import pl.grzegorz.bikerservice.BaselineIntegrationTest;
import pl.grzegorz.bikerservice.biker.dto.BikerDto;

import java.time.LocalDate;
import java.util.List;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static lombok.AccessLevel.PRIVATE;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
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
    void shouldReturnSingleBiker() throws Exception {
//        given
        BikerEntity bikerEntity = bikerRepository.save(firstBiker);
        long id = bikerEntity.getId();
//        when
//        When biker not found
        mockMvc.perform(get(URL + "/" + WRONG_ID))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andReturn();

//        When biker was found
        String mockResult = mockMvc.perform(get(URL + "/" + id))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        BikerOutputDto bikerOutputDto = objectMapper.readValue(mockResult, BikerOutputDto.class);
//        then
        assertAll(
                () -> assertNotNull(bikerOutputDto),
                () -> assertEquals("Piotr", bikerOutputDto.getFirstName()),
                () -> assertEquals("Piotrowski", bikerOutputDto.getLastName()),
                () -> assertEquals(LocalDate.of(1991, 9, 21), bikerOutputDto.getDateOfBirth()),
                () -> assertEquals(TRUE, bikerOutputDto.getIsActive())
        );

    }

    @Test
    void shouldReturnListOfBikerOutputDtoObjects() throws Exception {
//        given
        String page = "1";
        String size = "2";
        bikerRepository.save(secondBiker);
//        when
        String mockResult = mockMvc.perform(get(URL)
                        .param("page", page)
                        .param("size", size))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        List<BikerOutputDto> testResult = objectMapper.readValue(mockResult, new TypeReference<>() {
        });
//        then
        assertAll(
                () -> assertNotNull(testResult),
                () -> assertEquals(2, testResult.size()),
                () -> assertEquals("Piotr", testResult.get(0).getFirstName()),
                () -> assertEquals("Piotrowski", testResult.get(0).getLastName()),
                () -> assertEquals(LocalDate.of(1991, 9, 21), testResult.get(0).getDateOfBirth()),
                () -> assertEquals(TRUE, testResult.get(0).getIsActive()),
                () -> assertEquals("Adam", testResult.get(1).getFirstName()),
                () -> assertEquals("Adamowicz", testResult.get(1).getLastName()),
                () -> assertEquals(LocalDate.of(1984, 12, 27), testResult.get(1).getDateOfBirth()),
                () -> assertEquals(FALSE, testResult.get(1).getIsActive())
        );
    }

    @Test
    void shouldAddNewBikerToTheDatabase() throws Exception {
//        given
        String requestBody = objectMapper.writeValueAsString(bikerDto);
        mockMvc.perform(post(URL)
                        .contentType(APPLICATION_JSON)
                        .content(requestBody))
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn();

        List<BikerEntity> allBikers = bikerRepository.findAll();
        assertAll(
                () -> assertNotNull(allBikers),
                () -> assertEquals(2, allBikers.size()),
                () -> assertEquals("Piotr", allBikers.get(0).getFirstName()),
                () -> assertEquals("Piotrowski", allBikers.get(0).getLastName()),
                () -> assertEquals(LocalDate.of(1991, 9, 21), allBikers.get(0).getDateOfBirth()),
                () -> assertEquals(TRUE, allBikers.get(0).getIsActive()),
                () -> assertEquals("Paweł", allBikers.get(1).getFirstName()),
                () -> assertEquals("Pawłowski", allBikers.get(1).getLastName()),
                () -> assertEquals(LocalDate.of(1989, 3, 12), allBikers.get(1).getDateOfBirth()),
                () -> assertEquals(TRUE, allBikers.get(1).getIsActive())
        );
    }

    @Test
    void shouldEditBikerEntity() throws Exception {
//        given
        BikerEntity bikerEntity = bikerRepository.save(firstBiker);
        long id = bikerEntity.getId();
        String requestBody = objectMapper.writeValueAsString(bikerDto);
//        when
//        WHEN BIKER NOT FOUND
        mockMvc.perform(patch(URL + "/" + WRONG_ID)
                        .contentType(APPLICATION_JSON)
                        .content(requestBody))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andReturn();

//        WHEN BIKER WAS FOUND
        mockMvc.perform(patch(URL + "/" + id)
                        .contentType(APPLICATION_JSON)
                        .content(requestBody))
                .andDo(print())
                .andExpect(status().isNoContent())
                .andReturn();

        BikerEntity biker = bikerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Biker not found"));
//        then
        assertAll(
                () -> assertNotNull(biker),
                () -> assertEquals("Paweł", biker.getFirstName()),
                () -> assertEquals("Pawłowski", biker.getLastName()),
                () -> assertEquals("paweł@pawłowski.pl", biker.getEmail()),
                () -> assertEquals(TRUE, biker.getIsActive()),
                () -> assertEquals(LocalDate.of(1989, 3, 12), biker.getDateOfBirth())
        );
    }

    @Test
    void shouldRemoveBiker() throws Exception {
//        given
        long id = firstBiker.getId();
//        when
//        WHEN BIKER NOT FOUND
        mockMvc.perform(delete(URL + "/" + WRONG_ID))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andReturn();

//        WHEN BIKER WAS FOUND
        mockMvc.perform(delete(URL + "/" + id))
                .andDo(print())
                .andExpect(status().isNoContent())
                .andReturn();
        List<BikerEntity> bikers = bikerRepository.findAll();
//        then
        assertAll(
                () -> assertEquals(0, bikers.size()),
                () -> assertFalse(bikers.contains(firstBiker))
        );
    }

    @Getter(value = PRIVATE)
    @Setter(value = PRIVATE)
    private static class BikerOutputDto {

        private long id;
        private String firstName;
        private String lastName;
        private LocalDate dateOfBirth;
        private Boolean isActive;
    }
}