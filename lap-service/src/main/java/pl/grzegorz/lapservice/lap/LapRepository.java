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

    @Query("{'circuitDetails.track.trackId': ?0, 'lapDate':  {$gte: ?1, $lte: ?2}, 'motorcycleDetails.capacity':  {$gt: ?3, $lt: ?4}}")
    List<LapDocument> findAllByCircuitAndDateBetweenAndMotorcycleCapacityBetween(long trackId, LocalDate startDate,
                                                                                 LocalDate endDate, int startCapacity,
                                                                                 int endCapacity, Pageable pageable);
}