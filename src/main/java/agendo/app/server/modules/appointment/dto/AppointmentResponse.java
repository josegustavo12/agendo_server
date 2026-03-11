package agendo.app.server.modules.appointment.dto;

import java.time.LocalDateTime;

public record AppointmentResponse(
    Long id,
    UserSummary professional,
    UserSummary client,
    ServiceTypeSummary serviceType,
    Integer valueInCents,
    LocalDateTime scheduleDate,
    LocalDateTime requestDate
) {
    public record UserSummary(Long id, String name) {}
    public record ServiceTypeSummary(Long id, String name) {}
}
