package pl.grzegorz.bikerservice.role;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
interface RoleRepository extends JpaRepository<RoleEntity, Long> {

    Optional<RoleEntity> findByNameIgnoreCase(String name);
}
