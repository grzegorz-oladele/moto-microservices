package pl.grzegorz.motorcycleservice.motorcycle;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
interface MotorcycleQueryRepository extends JpaRepository<MotorcycleEntity, UUID> {
}
