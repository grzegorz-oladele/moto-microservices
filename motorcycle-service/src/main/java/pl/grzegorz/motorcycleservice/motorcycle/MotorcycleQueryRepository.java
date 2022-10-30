package pl.grzegorz.motorcycleservice.motorcycle;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.grzegorz.motorcycleservice.motorcycle.dto.output.MotorcycleOutputDto;

import java.util.List;
import java.util.Optional;

@Repository
interface MotorcycleQueryRepository extends JpaRepository<MotorcycleEntity, Long> {

    Optional<MotorcycleOutputDto> findAllById(long motorcycleId);

    List<MotorcycleOutputDto> findAllBy(Pageable pageable);
}
