package pl.grzegorz.bikerservice.biker;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface BikerRepository extends JpaRepository<BikerEntity, Long> {
}
