package pl.grzegorz.bikerservice.role;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
class RoleFacadeImpl implements RoleFacade {

    private final RoleRepository roleRepository;

    @Override
    public boolean existsByName(String name) {
        return roleRepository.existsByNameIgnoreCase(name);
    }
}
