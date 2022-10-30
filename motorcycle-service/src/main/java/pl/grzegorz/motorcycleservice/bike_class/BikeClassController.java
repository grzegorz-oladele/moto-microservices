package pl.grzegorz.motorcycleservice.bike_class;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.grzegorz.motorcycleservice.bike_class.dto.input.BikeClassDto;
import pl.grzegorz.motorcycleservice.bike_class.dto.output.BikeClassOutputDto;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/motorcycle-classes")
class BikeClassController {

    private final BikeClassFacade bikeClassFacade;

    @GetMapping
    ResponseEntity<List<BikeClassOutputDto>> getMotorcycleClasses(@RequestParam(value = "page") int page,
                                                                  @RequestParam(value = "size") int size) {
        return ResponseEntity.ok(bikeClassFacade.getMotorcycleClassList(page, size));
    }

    @GetMapping("/{motorcycleClassId}")
    ResponseEntity<BikeClassOutputDto> getMotorcycleClassById(@PathVariable long motorcycleClassId) {
        return ResponseEntity.ok(bikeClassFacade.getMotorcycleClassById(motorcycleClassId));
    }

    @PostMapping
    ResponseEntity<?> addNewMotorcycleClass(@RequestBody BikeClassDto bikeClassDto) {
        bikeClassFacade.addMotorcyclesClass(bikeClassDto);
        return ResponseEntity.created(URI.create("foo")).build();
    }

    @PatchMapping("/{motorcycleClassId}")
    ResponseEntity<?> editMotorcycleClass(@PathVariable long motorcycleClassId, @RequestBody BikeClassDto bikeClassDto) {
        bikeClassFacade.editMotorcycleClass(motorcycleClassId, bikeClassDto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{motorcycleClassId}")
    ResponseEntity<?> removeMotorcycleClass(@PathVariable long motorcycleClassId) {
        bikeClassFacade.removeMotorcycleClassById(motorcycleClassId);
        return ResponseEntity.noContent().build();
    }
}