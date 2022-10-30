package pl.grzegorz.motorcycleservice.motorcycle;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.grzegorz.motorcycleservice.motorcycle.dto.input.MotorcycleDto;
import pl.grzegorz.motorcycleservice.motorcycle.dto.output.MotorcycleOutputDto;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/motorcycles")
@RequiredArgsConstructor
class MotorcycleController {

    private final MotorcycleFacade motorcycleFacade;

    @GetMapping("/{motorcycleId}")
    ResponseEntity<MotorcycleOutputDto> getMotorcycleById(@PathVariable long motorcycleId) {
        return ResponseEntity.ok(motorcycleFacade.getMotorcycleById(motorcycleId));
    }

    @GetMapping
    ResponseEntity<List<MotorcycleOutputDto>> getListOfMotorcycles(@RequestParam int page, @RequestParam int size) {
        return ResponseEntity.ok(motorcycleFacade.getListOfMotorcycles(page, size));
    }

    @PostMapping("/{bikeClassId}/bike-classes")
    ResponseEntity<?> addMotorcycle(@RequestBody @Valid MotorcycleDto motorcycleDto, @PathVariable long bikeClassId) {
        motorcycleFacade.addMotorcycle(motorcycleDto, bikeClassId);
        return ResponseEntity.created(URI.create("foo")).build();
    }

    @PatchMapping("/{motorcycleId}")
    ResponseEntity<?> editMotorcycle(@RequestBody @Valid MotorcycleDto motorcycleDto, @PathVariable long motorcycleId) {
        motorcycleFacade.editMotorcycle(motorcycleId, motorcycleDto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{motorcycleId}")
    ResponseEntity<?> removeMotorcycle(@PathVariable long motorcycleId) {
        motorcycleFacade.removeMotorcycle(motorcycleId);
        return ResponseEntity.noContent().build();
    }
}