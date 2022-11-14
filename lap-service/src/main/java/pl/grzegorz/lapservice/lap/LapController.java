package pl.grzegorz.lapservice.lap;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.grzegorz.lapservice.lap.dto.input.DateDto;
import pl.grzegorz.lapservice.lap.dto.input.LapDto;
import pl.grzegorz.lapservice.lap.dto.output.LapDetailsByBiker;

import java.util.List;

import static java.net.URI.create;
import static org.springframework.http.ResponseEntity.*;

@RestController
@RequestMapping("/laps")
@RequiredArgsConstructor
class LapController {

    private final LapFacade lapFacade;

    @GetMapping("/{bikerId}/bikers")
    ResponseEntity<List<LapDetailsByBiker>> getLapsByBiker(@PathVariable long bikerId, @RequestParam int page,
                                                           @RequestParam int size) {
        return ok(lapFacade.getAllLapsByBiker(bikerId, page, size));
    }

    @GetMapping("/{bikerId}/bikers/{circuitId}/circuits")
    ResponseEntity<List<LapDetailsByBiker>> getAllLapsByBikerAndCircuitAndDateBetween(@PathVariable long bikerId,
                                                                                      @PathVariable long circuitId,
                                                                                      @RequestParam int page,
                                                                                      @RequestParam int size,
                                                                                      @RequestBody DateDto dateDto) {
        return ok(lapFacade.getAllLapsByBikerAndCircuitAndDateRange(bikerId, circuitId, page, size, dateDto));
    }

    @PostMapping("/{trackId}/tracks/{bikerId}/bikers/{motorcycleId}/motorcycles")
    ResponseEntity<?> addNewLap(@PathVariable long trackId, @PathVariable long bikerId,
                                @PathVariable long motorcycleId, @RequestBody LapDto lapDto) {
        lapFacade.addNewLap(trackId, bikerId, motorcycleId, lapDto);
        return created(create("foo")).build();
    }
}