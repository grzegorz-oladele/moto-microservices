package pl.grzegorz.lapservice.lap;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.grzegorz.lapservice.lap.dto.input.LapDto;

import java.net.URI;

@RestController
@RequestMapping("/laps")
@RequiredArgsConstructor
class LapController {

    private final LapFacade lapFacade;

    @PostMapping("/{circuitId}/circuits/{trackId}/tracks/{bikerId}/bikers/{motorcycleId}/motorcycles")
    ResponseEntity<?> addNewLap(@PathVariable long circuitId, @PathVariable long trackId, @PathVariable long bikerId,
                                @PathVariable long motorcycleId, @RequestBody LapDto lapDto) {
        lapFacade.addNewLap(circuitId, trackId, bikerId, motorcycleId, lapDto);
        return ResponseEntity.created(URI.create("foo")).build();
    }
}