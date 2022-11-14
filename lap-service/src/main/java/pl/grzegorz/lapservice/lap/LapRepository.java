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

    @Query("{'bikerDetails.$bikerId':  ?0, 'circuitDetails.$circuitId':  ?1, 'lapDate':  {$gt:  ?2, $lt: ?3}}")
    List<LapDocument> findAllByBikerDetails_IdAndCircuitDetails_CircuitIdAndLapDateBetween(long bikerId, long circuitId,
                                                                                           LocalDate startDate,
                                                                                           LocalDate endDate);
}
