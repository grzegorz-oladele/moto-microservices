package pl.grzegorz.circuitservice.track;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
interface TrackRepository extends JpaRepository<TrackEntity, Long> {

    Optional<TrackEntity> findByIdAndCircuit_Id(long trackId, long circuitId);

}
