package pl.grzegorz.bikerservice.biker;

import pl.grzegorz.bikerservice.biker.dto.BikerDto;
import pl.grzegorz.bikerservice.biker.dto.BikerOutputDto;

import java.util.List;

public interface BikerFacade {

    List<BikerOutputDto> getAllBikers(int page, int size);

    BikerOutputDto getBikerById(long bikerId);

    Boolean existBikerById(long bikerId);

    void addNewBiker(BikerDto bikerDto);

    void editBiker(long bikerId, BikerDto bikerDto);

    void removeBiker(long bikerId);
}
