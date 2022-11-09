package pl.grzegorz.circuitservice.track;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.grzegorz.circuitservice.track.dto.output.TrackOutputDto;

import java.util.List;
import java.util.Optional;

@Repository
interface TrackQueryRepository extends JpaRepository<TrackEntity, Long> {

    Optional<TrackOutputDto> findAllById(long trackId);

    List<TrackOutputDto> findAllBy(Pageable pageable);

    List<TrackOutputDto> findAllByCircuit_Id(long circuitId, Pageable pageable);
}
