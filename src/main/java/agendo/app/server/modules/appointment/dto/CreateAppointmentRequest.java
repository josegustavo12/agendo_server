package agendo.app.server.modules.appointment.dto;

import java.time.LocalDateTime;

public record CreateAppointmentRequest(
        Long serviceTypeId,
        Long professionalId,
        Long clientId,
        Integer valueInCents,
        LocalDateTime scheduleDate) {}
