package pl.grzegorz.bikerservice.biker;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.grzegorz.bikerservice.biker.dto.BikerOutputDto;

import java.util.List;
import java.util.Optional;

@Repository
interface BikerQueryRepository extends JpaRepository<BikerEntity, Long> {

    List<BikerOutputDto> getAllBy(Pageable pageable);
    Optional<BikerOutputDto> findAllById(long bikerId);
}
