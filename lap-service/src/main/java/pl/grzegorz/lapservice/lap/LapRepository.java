package pl.grzegorz.lapservice.lap;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
interface LapRepository extends MongoRepository<LapDocument, String> {
}
