package pl.grzegorz.circuitservice.circuit;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface CircuitRepository extends JpaRepository<CircuitEntity, Long> {
}
