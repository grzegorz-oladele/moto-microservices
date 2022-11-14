package pl.grzegorz.bikerservice.biker;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.grzegorz.bikerservice.biker.dto.BikerDto;
import pl.grzegorz.bikerservice.biker.dto.BikerOutputDto;
import pl.grzegorz.bikerservice.role.RoleFacade;
import pl.grzegorz.bikerservice.role.query.RoleSimpleEntity;
import pl.grzegorz.bikerservice.tools.validator.ValidatorFacade;

import java.util.List;
import java.util.Set;

import static java.lang.Boolean.FALSE;
import static org.springframework.data.domain.PageRequest.of;
import static pl.grzegorz.bikerservice.biker.BikerEntity.toBikerEntity;

@Service
@RequiredArgsConstructor
@Slf4j
class BikerFacadeImpl implements BikerFacade {

    private final BikerRepository bikerRepository;
    private final BikerQueryRepository bikerQueryRepository;
    private final RoleFacade roleFacade;
    private final ValidatorFacade validatorFacade;

    @Override
    public List<BikerOutputDto> getAllBikers(int page, int size) {
        validatorFacade.checkPageAndSizeValueAndThrowExceptionIfIsWrong(page, size);
        return bikerQueryRepository.getAllBy(of(page - 1, size));
    }

    @Override
    public BikerOutputDto getBikerById(long bikerId) {
        return bikerQueryRepository.findAllById(bikerId)
                .orElseThrow(() -> {
                    log.error("Biker with id -> {} not found", bikerId);
                    throw new IllegalArgumentException("Biker not found");
                });
    }

    @Override
    public Boolean existBikerById(long bikerId) {
        return bikerRepository.existsById(bikerId);
    }

    @Override
    public void addNewBiker(BikerDto bikerDto) {
        validatorFacade.checkDateOfBirthAndThrowExceptionIfIsWrong(bikerDto);
        BikerEntity biker = createBikerEntity(bikerDto);
        bikerRepository.save(biker);
        log.info("Save new biker with first name -> {}, last name -> {} and id -> {}", biker.getFirstName(),
                biker.getLastName(), biker.getId());
    }

    private BikerEntity createBikerEntity(BikerDto bikerDto) {
        BikerEntity biker = toBikerEntity(bikerDto);
//        RoleSimpleEntity simpleRole = getUserRoleSimpleEntity();
//        biker.setRoles(Set.of(simpleRole));
        return biker;
    }

    private RoleSimpleEntity getUserRoleSimpleEntity() {
        return roleFacade.getUserRoleSimpleEntity();
    }

    @Override
    public void editBiker(long bikerId, BikerDto bikerDto) {
        checkExistBiker(bikerId);
        validatorFacade.checkDateOfBirthAndThrowExceptionIfIsWrong(bikerDto);
        BikerEntity updatedBiker = toBikerEntity(bikerDto);
        updatedBiker.setId(bikerId);
        bikerRepository.save(updatedBiker);
        log.info("Updated biker with id -> {}", bikerId);
    }

    @Override
    public void removeBiker(long bikerId) {
        checkExistBiker(bikerId);
        bikerRepository.deleteById(bikerId);
        log.info("Remove biker with id -> {}", bikerId);
    }

    private void checkExistBiker(long bikerId) {
        if (existBikerById(bikerId).equals(FALSE)) {
            log.error("Biker with id -> {} not exist in database", bikerId);
            throw new IllegalArgumentException("Biker not exist in the database");
        }
    }
}