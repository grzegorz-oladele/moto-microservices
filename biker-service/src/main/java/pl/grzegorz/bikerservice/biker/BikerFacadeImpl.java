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
import java.util.stream.Collectors;

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
    public void addNewBiker(BikerDto bikerDto) {
        validatorFacade.checkDateOfBirthAndThrowExceptionIfIsWrong(bikerDto);
        BikerEntity biker = toBikerEntity(bikerDto);
        Set<RoleSimpleEntity> roles = getSetOfRoleSimpleEntity(bikerDto.getRoles());
        biker.setRoles(roles);
        bikerRepository.save(biker);
        log.info("Save new biker with first name -> {}, last name -> {} and id -> {}", biker.getFirstName(),
                biker.getLastName(), biker.getId());
    }

    private Set<RoleSimpleEntity> getSetOfRoleSimpleEntity(List<String> roles) {
        log.info("Mapping list of role names to list of RoleSimpleEntity");
        return roles.stream()
                .map(roleName -> {
                    checkRoleNameAndThrowExceptionIfNotExists(roleName);
                    return RoleSimpleEntity.builder()
                            .name(roleName)
                            .build();
                })
                .collect(Collectors.toSet());
    }

    private void checkRoleNameAndThrowExceptionIfNotExists(String roleName) {
        if (!existsRoleByName(roleName)) {
            log.error("Role with name -> {} not found", roleName);
            throw new IllegalArgumentException("Role not found");
        }
    }

    private boolean existsRoleByName(String name) {
        return roleFacade.existsByName(name);
    }

    @Override
    public void editBiker(long bikerId, BikerDto bikerDto) {
        existsBikerById(bikerId);
        validatorFacade.checkDateOfBirthAndThrowExceptionIfIsWrong(bikerDto);
        BikerEntity updatedBiker = toBikerEntity(bikerDto);
        Set<RoleSimpleEntity> roles = getSetOfRoleSimpleEntity(bikerDto.getRoles());
        updatedBiker.setRoles(roles);
        updatedBiker.setId(bikerId);
        bikerRepository.save(updatedBiker);
        log.info("Updated biker with id -> {}", bikerId);
    }

    @Override
    public void removeBiker(long bikerId) {
        existsBikerById(bikerId);
        bikerRepository.deleteById(bikerId);
        log.info("Remove biker with id -> {}", bikerId);
    }

    private void existsBikerById(long bikerId) {
        if (!bikerRepository.existsById(bikerId)) {
            log.error("Biker with id -> {} not found", bikerId);
            throw new IllegalArgumentException("Biker not found");
        }
    }
}