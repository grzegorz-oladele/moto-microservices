package pl.grzegorz.motorcycleservice.motorcycle_class;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.grzegorz.motorcycleservice.motorcycle_class.dto.input.MotorcycleClassDto;
import pl.grzegorz.motorcycleservice.motorcycle_class.dto.output.MotorcycleClassOutputDto;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/motorcycle-classes")
class MotorcycleClassController {

    private final MotorcycleClassFacade motorcycleClassFacade;

    @GetMapping
    ResponseEntity<List<MotorcycleClassOutputDto>> getMotorcycleClasses() {
        return ResponseEntity.ok(motorcycleClassFacade.getMotorcycleClassList());
    }

    @GetMapping("/{motorcycleClassId}")
    ResponseEntity<MotorcycleClassOutputDto> getMotorcycleClassById(@PathVariable long motorcycleClassId) {
        return ResponseEntity.ok(motorcycleClassFacade.getMotorcycleClassById(motorcycleClassId));
    }

    @PostMapping
    ResponseEntity<?> addNewMotorcycleClass(@RequestBody MotorcycleClassDto motorcycleClassDto) {
        motorcycleClassFacade.addMotorcyclesClass(motorcycleClassDto);
        return ResponseEntity.created(URI.create("foo")).build();
    }

    @PatchMapping("/{motorcycleClassId}")
    ResponseEntity<?> editMotorcycleClass(@PathVariable long motorcycleClassId, @RequestBody MotorcycleClassDto motorcycleClassDto) {
        motorcycleClassFacade.editMotorcycleClass(motorcycleClassId, motorcycleClassDto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{motorcycleClassId}")
    ResponseEntity<?> removeMotorcycleClass(@PathVariable long motorcycleClassId) {
        motorcycleClassFacade.removeMotorcycleClassById(motorcycleClassId);
        return ResponseEntity.noContent().build();
    }
}