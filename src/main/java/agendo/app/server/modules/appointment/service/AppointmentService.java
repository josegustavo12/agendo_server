package agendo.app.server.modules.appointment.service;

import java.util.List;

import org.springframework.stereotype.Service;

import agendo.app.server.modules.appointment.models.AppointmentEntity;
import agendo.app.server.modules.appointment.repository.AppointmentRepository;
import agendo.app.server.modules.user.models.UserEntity;
import agendo.app.server.modules.user.service.UserService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final UserService userService;

    public AppointmentEntity create(AppointmentEntity appointment) {
        return appointmentRepository.save(appointment);
    }

    public AppointmentEntity findById(Long id) {
        return appointmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Appointment not found: " + id));
    }
    public List<AppointmentEntity> findAll() {
        return appointmentRepository.findAll();
    }

    public List<AppointmentEntity> findByProfessional(Long professionalId) {
        UserEntity professional = userService.findById(professionalId);
        return appointmentRepository.findByProfessional(professional);
    }

    public List<AppointmentEntity> findByClient(Long clientId) {
        UserEntity client = userService.findById(clientId);
        return appointmentRepository.findByClient(client);
    }
}
