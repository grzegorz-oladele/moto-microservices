package pl.grzegorz.motorcycleservice.motorcycle;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface MotorcycleRepository extends JpaRepository<MotorcycleEntity, Long> {
}
