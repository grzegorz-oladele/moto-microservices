package pl.grzegorz.circuitservice.circuit;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.grzegorz.circuitservice.circuit.dto.input.CircuitDto;
import pl.grzegorz.circuitservice.circuit.dto.output.CircuitOutputDto;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

import static org.springframework.http.ResponseEntity.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/circuits")
class CircuitController {

    private final CircuitFacade circuitFacade;

    @GetMapping("/{circuitId}")
    ResponseEntity<CircuitOutputDto> getCircuitById(@PathVariable long circuitId) {
        return ok(circuitFacade.getCircuitById(circuitId));
    }

    @GetMapping
    ResponseEntity<List<CircuitOutputDto>> getAllCircuits(@RequestParam int page, @RequestParam int size) {
        return ok(circuitFacade.getAllCircuits(page, size));
    }

    @PostMapping
    ResponseEntity<?> addNewCircuit(@RequestBody @Valid CircuitDto circuitDto) {
        circuitFacade.addNewCircuit(circuitDto);
        return created(URI.create("foo")).build();
    }

    @PatchMapping("/{circuitId}")
    ResponseEntity<?> editCircuitById(@PathVariable long circuitId, @RequestBody @Valid CircuitDto circuitDto) {
        circuitFacade.editCircuit(circuitId, circuitDto);
        return noContent().build();
    }

    @DeleteMapping("/{circuitId}")
    ResponseEntity<?> removeCircuitById(@PathVariable long circuitId) {
        circuitFacade.removeCircuit(circuitId);
        return noContent().build();
    }
}