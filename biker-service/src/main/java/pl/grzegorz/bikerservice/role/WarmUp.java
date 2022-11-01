package pl.grzegorz.bikerservice.role;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class WarmUp implements CommandLineRunner {

    private final RoleRepository roleRepository;

    @Override
    public void run(String... args) {
        if (roleRepository.findAll().isEmpty()) {
            roleRepository.save(RoleEntity.builder().name("USER").build());
        }
    }
}