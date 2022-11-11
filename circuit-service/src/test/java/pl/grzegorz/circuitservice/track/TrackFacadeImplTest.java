package pl.grzegorz.circuitservice.track;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.grzegorz.circuitservice.circuit.CircuitFacade;
import pl.grzegorz.circuitservice.circuit.query.CircuitSimpleEntity;
import pl.grzegorz.circuitservice.track.dto.input.TrackDto;
import pl.grzegorz.circuitservice.track.dto.output.TrackOutputDto;
import pl.grzegorz.circuitservice.track.dto.output.TrackSimpleOutputDto;

import java.util.List;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static java.util.Optional.of;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.data.domain.PageRequest.of;
import static pl.grzegorz.circuitservice.circuit.CircuitTestInitValue.getCircuitSimpleEntity;
import static pl.grzegorz.circuitservice.track.TrackTestInitValue.*;

@ExtendWith(MockitoExtension.class)
class TrackFacadeImplTest {

    @InjectMocks
    private TrackFacadeImpl trackFacade;
    @Mock
    private TrackRepository trackRepository;
    @Mock
    private TrackQueryRepository trackQueryRepository;
    @Mock
    private CircuitFacade circuitFacade;

    private final long trackId = 1;
    private final long circuitId = 2;
    private final int page = 1;
    private final int size = 2;

    private List<TrackOutputDto> listOfTracks;
    private TrackSimpleOutputDto trackSimpleOutputDto;
    private CircuitSimpleEntity circuitSimpleEntity;
    private TrackDto trackDto;
    private TrackEntity track;

    @BeforeEach
    void setup() {
        listOfTracks = getTrackOutputDtoList();
        trackSimpleOutputDto = getTrackSimpleOutputDto();
        circuitSimpleEntity = getCircuitSimpleEntity();
        trackDto = getTrackDto();
        track = getTrackEntity();
    }

    @Test
    void shouldReturnTrackOutputDtoObjectById() {
//        given
        when(trackQueryRepository.findAllById(trackId)).thenReturn(of(listOfTracks.get(0)));
//        when
        TrackOutputDto trackById = trackFacade.getTrackById(trackId);
//        then
        assertAll(
                () -> assertNotNull(trackById),
                () -> assertEquals(1L, trackById.getId()),
                () -> assertEquals("Tor nr 1", trackById.getName()),
                () -> assertEquals(1923D, trackById.getTrackLength()),
                () -> assertEquals(TRUE, trackById.getIsThereFence()),
                () -> assertEquals(FALSE, trackById.getIsTherePlaceForAd()),
                () -> assertEquals(1L, trackById.getCircuit().getId()),
                () -> assertEquals("Tor Poznań", trackById.getCircuit().getName())
        );
    }

    @Test
    void shouldReturnListOfTrackOutputDto() {
//        given
        when(trackQueryRepository.findAllBy(of(page - 1, size))).thenReturn(listOfTracks);
//        when
        List<TrackOutputDto> allTracks = trackFacade.getAllTracks(page, size);
//        then
        assertAll(
                () -> assertNotNull(allTracks),
                () -> assertEquals(2, allTracks.size()),
                () -> assertEquals(1L, allTracks.get(0).getId()),
                () -> assertEquals("Tor nr 1", allTracks.get(0).getName()),
                () -> assertEquals(1923D, allTracks.get(0).getTrackLength()),
                () -> assertEquals(TRUE, allTracks.get(0).getIsThereFence()),
                () -> assertEquals(FALSE, allTracks.get(0).getIsTherePlaceForAd()),
                () -> assertEquals(1L, allTracks.get(0).getCircuit().getId()),
                () -> assertEquals("Tor Poznań", allTracks.get(0).getCircuit().getName()),
                () -> assertEquals(2L, allTracks.get(1).getId()),
                () -> assertEquals("Tor nr 2", allTracks.get(1).getName()),
                () -> assertEquals(2937D, allTracks.get(1).getTrackLength()),
                () -> assertEquals(FALSE, allTracks.get(1).getIsThereFence()),
                () -> assertEquals(TRUE, allTracks.get(1).getIsTherePlaceForAd()),
                () -> assertEquals(2L, allTracks.get(1).getCircuit().getId()),
                () -> assertEquals("Tor Jastrząb", allTracks.get(1).getCircuit().getName())
        );
    }

    @Test
    void shouldReturnListOfTrackOutputDtoObjectsByCircuit() {
//        given
        when(trackQueryRepository.findAllByCircuit_Id(circuitId, of(page - 1, size)))
                .thenReturn(List.of(trackSimpleOutputDto));
//        when
        List<TrackSimpleOutputDto> allTracksByCircuit = trackFacade.getAllTracksByCircuit(circuitId, page, size);
//        then
        assertAll(
                () -> assertNotNull(allTracksByCircuit),
                () -> assertEquals(1, allTracksByCircuit.size()),
                () -> assertEquals(1L, allTracksByCircuit.get(0).getId()),
                () -> assertEquals("Tor nr 1", allTracksByCircuit.get(0).getName()),
                () -> assertEquals(1923D, allTracksByCircuit.get(0).getTrackLength())
        );
    }

    @Test
    void shouldCallSaveMethodFromTrackRepositoryByAddTrackById() {
//        given
        when(circuitFacade.getCircuitSimpleEntityById(circuitId)).thenReturn(circuitSimpleEntity);
//        when
        trackFacade.addTrack(circuitId, trackDto);
//        then
        verify(trackRepository).save(any(TrackEntity.class));
    }

    @Test
    void shouldCallSaveMethodFromTrackRepositoryByEditTrackById() {
//        given
        when(trackRepository.findById(trackId)).thenReturn(of(track));
//        when
        trackFacade.editTrack(trackId, trackDto);
//        then
        verify(trackRepository).save(any(TrackEntity.class));
    }

    @Test
    void shouldCallDeleteMethodFromTrackRepositoryByRemoveTrackById() {
//        given
        when(trackRepository.existsById(trackId)).thenReturn(true);
//        when
        trackFacade.removeTrack(trackId);
//        then
        verify(trackRepository).deleteById(trackId);
    }
}