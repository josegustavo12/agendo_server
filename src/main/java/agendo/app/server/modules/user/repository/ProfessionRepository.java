package agendo.app.server.modules.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import agendo.app.server.modules.user.models.ProfessionEntity;

public interface ProfessionRepository extends JpaRepository<ProfessionEntity, Long> {
    Optional<ProfessionEntity> findByName(String name);
}
