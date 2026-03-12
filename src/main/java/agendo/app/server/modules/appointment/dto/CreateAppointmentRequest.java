package agendo.app.server.modules.appointment.dto;

import java.time.LocalDateTime;
import java.util.List;

public record CreateAppointmentRequest(
    Long professionalId,
    Long clientId,
    List<Long> serviceTypeIds,
    LocalDateTime scheduleDate
) {}
