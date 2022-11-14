package pl.grzegorz.lapservice.lap;

import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
interface LapRepository extends MongoRepository<LapDocument, String> {

    List<LapDocument> findAllByBikerDetails_Id(long bikerId, Pageable pageable);

    @Query("{'bikerDetails.id': ?0, 'circuitDetails.circuitId': ?1, 'lapDate':  {$gte: ?2, $lte: ?3}}")
    List<LapDocument> findAllByBikerAndCircuitAndDateBetween(long bikerId, long circuitId, LocalDate startDate,
                                                             LocalDate endDate, Pageable pageable);
}