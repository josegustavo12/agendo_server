package agendo.app.server.modules.appointment.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import agendo.app.server.modules.appointment.models.AppointmentServiceEntity;

public interface AppointmentServiceRepository extends JpaRepository<AppointmentServiceEntity, Long> {
    List<AppointmentServiceEntity> findByAppointmentId(Long appointmentId);
}
