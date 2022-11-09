package pl.grzegorz.circuitservice.track;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface TrackRepository extends JpaRepository<TrackEntity, Long> {
}
