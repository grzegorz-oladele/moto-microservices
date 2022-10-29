package pl.grzegorz.motorcycleservice.motorcycle;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.grzegorz.motorcycleservice.motorcycle.dto.input.MotorcycleDto;
import pl.grzegorz.motorcycleservice.motorcycle.dto.output.MotorcycleOutputDto;

import java.net.URI;

@RestController
@RequestMapping("/motorcycles")
@RequiredArgsConstructor
class MotorcycleController {

    private final MotorcycleFacade motorcycleFacade;

    @GetMapping("/{motorcycleId}")
    ResponseEntity<MotorcycleOutputDto> getMotorcycleById(@PathVariable long motorcycleId) {
        return ResponseEntity.ok(motorcycleFacade.getMotorcycleById(motorcycleId));
    }

    @PostMapping("/{bikeClassId}/bike-classes")
    ResponseEntity<?> addNewMotorcycle(@RequestBody MotorcycleDto motorcycleDto, @PathVariable long bikeClassId) {
        motorcycleFacade.AddMotorcycle(motorcycleDto, bikeClassId);
        return ResponseEntity.created(URI.create("foo")).build();
    }
}