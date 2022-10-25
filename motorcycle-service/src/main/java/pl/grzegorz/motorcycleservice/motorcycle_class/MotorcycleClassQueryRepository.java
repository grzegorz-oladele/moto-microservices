package pl.grzegorz.motorcycleservice.motorcycle_class;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.grzegorz.motorcycleservice.motorcycle_class.dto.output.MotorcycleClassOutputDto;

import java.util.List;
import java.util.Optional;

@Repository
interface MotorcycleClassQueryRepository extends JpaRepository<MotorcycleClassEntity, Long> {

    List<MotorcycleClassOutputDto> findAllBy();
    Optional<MotorcycleClassOutputDto> findAllById(long motorcycleClassId);
}
