package agendo.app.server.modules.appointment.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import agendo.app.server.modules.appointment.models.ServiceTypeEntity;
import agendo.app.server.modules.user.models.UserEntity;

public interface ServiceTypeRepository extends JpaRepository<ServiceTypeEntity, Long> {
    List<ServiceTypeEntity> findByOwner(UserEntity owner);
    Optional<ServiceTypeEntity> findByIdAndOwner(Long id, UserEntity owner);
    List<ServiceTypeEntity> findByOwnerId(Long ownerId);
}
