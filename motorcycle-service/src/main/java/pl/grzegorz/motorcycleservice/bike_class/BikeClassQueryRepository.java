package pl.grzegorz.motorcycleservice.bike_class;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.grzegorz.motorcycleservice.bike_class.dto.output.BasicBikeClassOutputDto;
import pl.grzegorz.motorcycleservice.bike_class.dto.output.BikeClassOutputDto;

import java.util.List;
import java.util.Optional;

@Repository
interface BikeClassQueryRepository extends JpaRepository<BikeClassEntity, Long> {

    List<BikeClassOutputDto> findAllBy();
    Optional<BikeClassOutputDto> findAllById(long motorcycleClassId);
}
