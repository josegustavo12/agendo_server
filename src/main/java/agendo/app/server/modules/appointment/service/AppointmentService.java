package agendo.app.server.modules.appointment.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import agendo.app.server.modules.appointment.models.AppointmentEntity;
import agendo.app.server.modules.appointment.models.AppointmentServiceEntity;
import agendo.app.server.modules.appointment.models.ServiceTypeEntity;
import agendo.app.server.modules.appointment.repository.AppointmentRepository;
import agendo.app.server.modules.appointment.repository.AppointmentServiceRepository;
import agendo.app.server.modules.user.models.UserEntity;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final AppointmentServiceRepository appointmentServiceRepository;

    @Transactional
    public AppointmentEntity create(AppointmentEntity appointment, List<ServiceTypeEntity> serviceTypes) {
        BigDecimal total = serviceTypes.stream()
                .map(ServiceTypeEntity::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        appointment.setTotalAmount(total);
        AppointmentEntity saved = appointmentRepository.save(appointment);

        serviceTypes.forEach(st -> appointmentServiceRepository.save(
                AppointmentServiceEntity.builder()
                        .appointment(saved)
                        .serviceType(st)
                        .build()
        ));

        return saved;
    }

    public AppointmentEntity findByIdAndParticipant(Long id, UserEntity user) {
        return appointmentRepository.findByIdAndParticipant(id, user)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Appointment not found: " + id));
    }

    public List<AppointmentEntity> findByParticipant(UserEntity user) {
        return appointmentRepository.findByParticipant(user);
    }

    public List<AppointmentEntity> findByRole(UserEntity user, String role) {
        return switch (role) {
            case "professional" -> appointmentRepository.findByProfessional(user);
            case "client" -> appointmentRepository.findByClient(user);
            default -> throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Role inválida. Use 'professional' ou 'client'");
        };
    }
}
