package agendo.app.server.modules.user.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import agendo.app.server.modules.user.models.UserEntity;
import agendo.app.server.modules.user.models.UserRole;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    List<UserEntity> findByRole(UserRole role);
    boolean existsByEmail(String email);
    Optional<UserEntity> findByToken(String token);
    Optional<UserEntity> findByEmail(String email);
}
