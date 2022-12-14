package pl.grzegorz.lapservice.lap;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.grzegorz.lapservice.biker.BikerFeignFacade;
import pl.grzegorz.lapservice.biker.details.BikerDetails;
import pl.grzegorz.lapservice.circuit.CircuitFeignFacade;
import pl.grzegorz.lapservice.circuit.details.CircuitDetails;
import pl.grzegorz.lapservice.lap.dto.input.DateDto;
import pl.grzegorz.lapservice.lap.dto.input.DateLapDto;
import pl.grzegorz.lapservice.lap.dto.input.LapDto;
import pl.grzegorz.lapservice.lap.dto.output.LapDetailsByBiker;
import pl.grzegorz.lapservice.lap.dto.output.LapDetailsByTrackOutputDto;
import pl.grzegorz.lapservice.motorcycle.MotorcycleFeignFacade;
import pl.grzegorz.lapservice.motorcycle.details.MotorcycleDetails;

import java.time.LocalDate;
import java.util.List;

import static java.time.LocalDate.parse;
import static org.springframework.data.domain.PageRequest.of;
import static pl.grzegorz.lapservice.lap.LapDocument.toLapDocument;
import static pl.grzegorz.lapservice.lap.LapMapper.*;
import static pl.grzegorz.lapservice.lap.LapMapper.toLapDetailsByBikerList;

@Service
@RequiredArgsConstructor
@Slf4j
class LapFacadeImpl implements LapFacade {

    private final LapRepository lapRepository;
    private final MotorcycleFeignFacade motorcycleFeignFacade;
    private final CircuitFeignFacade circuitFeignFacade;
    private final BikerFeignFacade bikerFeignFacade;

    @Override
    public List<LapDetailsByBiker> getAllLapsByBiker(long bikerId, int page, int size) {
        List<LapDocument> lapsOfBiker = lapRepository.findAllByBikerDetails_Id(bikerId, of(page - 1, size));
        return toLapDetailsByBikerList(lapsOfBiker);
    }

    @Override
    public List<LapDetailsByBiker> getAllLapsByBikerAndCircuitAndDateRange(long bikerId, long circuitId, int page,
                                                                           int size, DateDto dateDto) {
        LocalDate startDate = parse(dateDto.getStartLapDate());
        LocalDate endDate = parse(dateDto.getEndLapDate());
        List<LapDocument> allLapsByBikerAndCircuitAndDateBetween =
                lapRepository.findAllByBikerAndCircuitAndDateBetween(bikerId, circuitId, startDate, endDate,
                        of(page - 1, size));
        return toLapDetailsByBikerList(allLapsByBikerAndCircuitAndDateBetween);
    }

    @Override
    public void addNewLap(long trackId, long bikerId, long motorcycleId, LapDto lapDto) {
        LapDocument lap = createLap(trackId, bikerId, motorcycleId, lapDto);
        log.info(lap.toString());
        lapRepository.save(lap);
    }

    @Override
    public LapDetailsByTrackOutputDto getAllLapsByCircuitAndDateRangeAndMotorcycleCapacityRange(long trackId, DateLapDto dateDto,
                                                                                                      int page, int size) {
        LocalDate startDate = parse(dateDto.getStartDate());
        LocalDate endDate = parse(dateDto.getEndDate());
        int startCapacity = dateDto.getStartCapacity();
        int endCapacity = dateDto.getEndCapacity();
        List<LapDocument> lapsByCircuitDateAndCapacityRange =
        lapRepository.findAllByCircuitAndDateBetweenAndMotorcycleCapacityBetween(trackId, startDate, endDate,
                startCapacity, endCapacity, of(page - 1, size));
        return toLapDetailsByTrackOutputDto(lapsByCircuitDateAndCapacityRange, startDate, endDate);
    }

    private LapDocument createLap(long trackId, long bikerId, long motorcycleId, LapDto lapDto) {
        BikerDetails bikerDetails = getBikerDetails(bikerId);
        MotorcycleDetails motorcycleDetails = getMotorcycleById(motorcycleId);
        checkMotorcycleBelongToBikerAndThrowExceptionIfNot(bikerDetails, motorcycleDetails);
        CircuitDetails circuitDetails = getCircuitDetails(trackId);
        LapDocument newLap = toLapDocument(lapDto);
        newLap.setCircuitDetails(circuitDetails);
        newLap.setBikerDetails(bikerDetails);
        newLap.setMotorcycleDetails(motorcycleDetails);
        return newLap;
    }

    private void checkMotorcycleBelongToBikerAndThrowExceptionIfNot(BikerDetails bikerDetails,
                                                                    MotorcycleDetails motorcycleDetails) {
        if (!bikerDetails.getId().equals(motorcycleDetails.getBikerId())) {
            log.error("Motorcycle with id -> {} not belong to biker with id -> {}", motorcycleDetails.getMotorcycleId(),
                    bikerDetails.getId());
            throw new IllegalArgumentException("Motorcycle not belong to biker");
        }
    }

    private CircuitDetails getCircuitDetails(long trackId) {
        return circuitFeignFacade.getCircuitDetails(trackId);
    }

    private BikerDetails getBikerDetails(long bikerId) {
        return bikerFeignFacade.getBikerById(bikerId);
    }

    private MotorcycleDetails getMotorcycleById(long motorcycleId) {
        return motorcycleFeignFacade.getMotorcycleById(motorcycleId);
    }
}