package pl.grzegorz.lapservice.lap;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.grzegorz.lapservice.lap.dto.input.LapDto;

import static java.net.URI.create;
import static org.springframework.http.ResponseEntity.*;

@RestController
@RequestMapping("/laps")
@RequiredArgsConstructor
class LapController {

    private final LapFacade lapFacade;

    @PostMapping("/{trackId}/tracks/{bikerId}/bikers/{motorcycleId}/motorcycles")
    ResponseEntity<?> addNewLap(@PathVariable long trackId, @PathVariable long bikerId,
                                @PathVariable long motorcycleId, @RequestBody LapDto lapDto) {
        lapFacade.addNewLap(trackId, bikerId, motorcycleId, lapDto);
        return created(create("foo")).build();
    }
}