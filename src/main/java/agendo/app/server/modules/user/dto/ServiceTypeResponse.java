package agendo.app.server.modules.user.dto;

import java.math.BigDecimal;

public record ServiceTypeResponse(
    Long id,
    String name,
    String description,
    BigDecimal price
) {}
