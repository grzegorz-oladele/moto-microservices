package pl.grzegorz.circuitservice.circuit;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.grzegorz.circuitservice.circuit.dto.output.CircuitOutputDto;

import java.util.List;
import java.util.Optional;

@Repository
interface CircuitQueryRepository extends JpaRepository<CircuitEntity, Long> {

    Optional<CircuitOutputDto> findAllById(long id);
    List<CircuitOutputDto> findAllBy(Pageable pageable);
}
