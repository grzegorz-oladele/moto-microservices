package pl.grzegorz.circuitservice.track;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.grzegorz.circuitservice.track.dto.input.TrackDto;
import pl.grzegorz.circuitservice.track.dto.output.TrackOutputDto;
import pl.grzegorz.circuitservice.track.dto.output.TrackSimpleOutputDto;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

import static org.springframework.http.ResponseEntity.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tracks")
class TrackController {

    private final TrackFacade trackFacade;

    @GetMapping("/{trackId}")
    ResponseEntity<TrackOutputDto> getTrackById(@PathVariable long trackId) {
        return ok(trackFacade.getTrackById(trackId));
    }

    @GetMapping
    ResponseEntity<List<TrackOutputDto>> getAllTracks(@RequestParam int page, @RequestParam int size) {
        return ok(trackFacade.getAllTracks(page, size));
    }

    @GetMapping("/{circuitId}/circuits")
    ResponseEntity<List<TrackSimpleOutputDto>> getAllTracksByCircuit(@PathVariable long circuitId, @RequestParam int page,
                                                                     @RequestParam int size) {
        return ok(trackFacade.getAllTracksByCircuit(circuitId, page, size));
    }

    @PostMapping("/{circuitId}/circuits")
    ResponseEntity<?> addNewTrack(@PathVariable long circuitId, @RequestBody @Valid TrackDto trackDto) {
        trackFacade.addTrack(circuitId, trackDto);
        return created(URI.create("foo")).build();
    }

    @PatchMapping("/{trackId}")
    ResponseEntity<?> editTrack(@PathVariable long trackId, @RequestBody @Valid TrackDto trackDto) {
        trackFacade.editTrack(trackId, trackDto);
        return noContent().build();
    }

    @DeleteMapping("/{trackId}")
    ResponseEntity<?> removeTrack(@PathVariable long trackId) {
        trackFacade.removeTrack(trackId);
        return noContent().build();
    }
}