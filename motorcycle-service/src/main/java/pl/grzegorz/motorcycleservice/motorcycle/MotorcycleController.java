package pl.grzegorz.motorcycleservice.motorcycle;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping
    ResponseEntity<?> addNewMotorcycle(@RequestBody MotorcycleDto motorcycleDto) {
        motorcycleFacade.AddMotorcycle(motorcycleDto);
        return ResponseEntity.created(URI.create("foo")).build();
    }
}