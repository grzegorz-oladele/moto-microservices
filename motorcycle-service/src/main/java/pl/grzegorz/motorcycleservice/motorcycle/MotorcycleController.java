package pl.grzegorz.motorcycleservice.motorcycle;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.grzegorz.motorcycleservice.motorcycle.dto.input.MotorcycleDto;
import pl.grzegorz.motorcycleservice.motorcycle.dto.output.MotorcycleLapOutputDto;
import pl.grzegorz.motorcycleservice.motorcycle.dto.output.MotorcycleOutputDto;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

import static org.springframework.http.ResponseEntity.*;

@RestController
@RequestMapping("/motorcycles")
@RequiredArgsConstructor
class MotorcycleController {

    private final MotorcycleFacade motorcycleFacade;

    @GetMapping("/{motorcycleId}")
    ResponseEntity<MotorcycleOutputDto> getMotorcycleById(@PathVariable long motorcycleId) {
        return ok(motorcycleFacade.getMotorcycleById(motorcycleId));
    }

    @GetMapping
    ResponseEntity<List<MotorcycleOutputDto>> getListOfMotorcycles(@RequestParam int page, @RequestParam int size) {
        return ok(motorcycleFacade.getListOfMotorcycles(page, size));
    }

    @GetMapping("/{motorcycleId}/laps")
    ResponseEntity<MotorcycleLapOutputDto> getMotorcycleToLap(@PathVariable long motorcycleId) {
        return ok(motorcycleFacade.getMotorcycleToLap(motorcycleId));
    }

    @PostMapping("/{bikerId}/bikers/{motorcycleClassId}/bike-classes")
    ResponseEntity<?> addMotorcycle(@PathVariable long bikerId, @PathVariable long motorcycleClassId,
                                    @RequestBody @Valid MotorcycleDto motorcycleDto) {
        motorcycleFacade.addMotorcycle(bikerId, motorcycleClassId, motorcycleDto);
        return created(URI.create("foo")).build();
    }

    @PatchMapping("/{motorcycleId}")
    ResponseEntity<?> editMotorcycle(@RequestBody @Valid MotorcycleDto motorcycleDto, @PathVariable long motorcycleId) {
        motorcycleFacade.editMotorcycle(motorcycleId, motorcycleDto);
        return noContent().build();
    }

    @DeleteMapping("/{motorcycleId}")
    ResponseEntity<?> removeMotorcycle(@PathVariable long motorcycleId) {
        motorcycleFacade.removeMotorcycle(motorcycleId);
        return noContent().build();
    }
}