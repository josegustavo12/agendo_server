package agendo.app.server.modules.appointment.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import agendo.app.server.modules.appointment.models.ServiceTypeEntity;

public interface ServiceTypeRepository extends JpaRepository<ServiceTypeEntity, Long> {
    boolean existsByName(String name);
}
