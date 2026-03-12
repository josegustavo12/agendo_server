package agendo.app.server.modules.rating.dto;

import java.time.LocalDateTime;

public record RatingResponse(
    Long id,
    Integer score,
    String comment,
    Long clientId,
    String clientName,
    Long professionalId,
    String professionalName,
    LocalDateTime createdAt
) {}

