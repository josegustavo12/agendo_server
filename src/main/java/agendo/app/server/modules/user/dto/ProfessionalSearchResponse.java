package agendo.app.server.modules.user.dto;

import java.math.BigDecimal;

public record ProfessionalSearchResponse(
    Long id,
    String name,
    String phone,
    String professionName,
    String bio,
    BigDecimal hourlyRate,
    Double ratingAverage,
    Boolean isAvailable
) {}
