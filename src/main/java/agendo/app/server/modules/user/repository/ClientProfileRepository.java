package agendo.app.server.modules.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import agendo.app.server.modules.user.models.ClientProfileEntity;
import agendo.app.server.modules.user.models.UserEntity;

public interface ClientProfileRepository extends JpaRepository<ClientProfileEntity, Long> {
    Optional<ClientProfileEntity> findByUser(UserEntity user);
}
