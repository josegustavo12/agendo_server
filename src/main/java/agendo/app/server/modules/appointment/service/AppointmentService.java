package agendo.app.server.modules.appointment.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import agendo.app.server.modules.appointment.models.AppointmentEntity;
import agendo.app.server.modules.appointment.repository.AppointmentRepository;
import agendo.app.server.modules.user.models.UserEntity;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;

    public AppointmentEntity create(AppointmentEntity appointment) {
        return appointmentRepository.save(appointment);
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
