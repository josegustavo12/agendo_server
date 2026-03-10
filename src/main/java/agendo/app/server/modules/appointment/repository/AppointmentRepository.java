package agendo.app.server.modules.appointment.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import agendo.app.server.modules.appointment.models.AppointmentEntity;
import agendo.app.server.modules.user.models.UserEntity;

public interface AppointmentRepository extends JpaRepository<AppointmentEntity, Long> {
    List<AppointmentEntity> findByProfessional(UserEntity professional);
    List<AppointmentEntity> findByClient(UserEntity client);
}
