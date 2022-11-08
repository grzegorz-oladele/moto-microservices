package pl.grzegorz.bikerservice.role;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.grzegorz.bikerservice.role.query.RoleSimpleEntity;

@Service
@RequiredArgsConstructor
@Slf4j
class RoleFacadeImpl implements RoleFacade {

    private final RoleRepository roleRepository;

    @Override
    public RoleSimpleEntity getUserRoleSimpleEntity() {
        RoleEntity role = getUserRole();
        return RoleSimpleEntity.builder()
                .name(role.getName())
                .build();
    }

    private RoleEntity getUserRole() {
        return roleRepository.findByNameIgnoreCase("USER")
                .orElseThrow(() -> {
                    log.error("Role with name USER not found");
                    throw new IllegalArgumentException("Role not found");
                });
    }
}
