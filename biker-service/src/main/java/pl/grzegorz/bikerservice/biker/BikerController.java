package pl.grzegorz.bikerservice.biker;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.grzegorz.bikerservice.biker.dto.BikerDto;
import pl.grzegorz.bikerservice.biker.dto.BikerOutputDto;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

import static org.springframework.http.ResponseEntity.*;

@RestController
@RequestMapping("/bikers")
@RequiredArgsConstructor
class BikerController {

    private final BikerFacade bikerFacade;

    @GetMapping
    ResponseEntity<List<BikerOutputDto>> getAllBikers(@RequestParam int page, @RequestParam int size) {
        return ok(bikerFacade.getAllBikers(page, size));
    }

    @GetMapping("/{bikerId}")
    ResponseEntity<BikerOutputDto> getBikerById(@PathVariable long bikerId) {
        return ok(bikerFacade.getBikerById(bikerId));
    }

    @PostMapping
    ResponseEntity<?> addNewBiker(@RequestBody @Valid BikerDto bikerDto) {
        bikerFacade.addNewBiker(bikerDto);
        return created(URI.create("foo")).build();
    }

    @PatchMapping("/{bikerId}")
    ResponseEntity<?> editBikerById(@PathVariable long bikerId, @RequestBody @Valid BikerDto bikerDto) {
        bikerFacade.editBiker(bikerId, bikerDto);
        return noContent().build();
    }

    @DeleteMapping("/{bikerId}")
    ResponseEntity<?> deleteBikerById(@PathVariable long bikerId) {
        bikerFacade.removeBiker(bikerId);
        return noContent().build();
    }
}
