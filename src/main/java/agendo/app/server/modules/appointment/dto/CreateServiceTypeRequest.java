package agendo.app.server.modules.appointment.dto;

import java.math.BigDecimal;

public record CreateServiceTypeRequest(String name, String description, BigDecimal price) {}
