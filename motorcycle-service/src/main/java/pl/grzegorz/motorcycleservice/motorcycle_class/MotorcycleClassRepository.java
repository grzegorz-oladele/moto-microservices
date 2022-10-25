package pl.grzegorz.motorcycleservice.motorcycle_class;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface MotorcycleClassRepository extends JpaRepository<MotorcycleClassEntity, Long> {
}
