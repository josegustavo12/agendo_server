package agendo.app.server.modules.user.dto;

import java.math.BigDecimal;

import agendo.app.server.modules.user.models.UserRole;

public record CreateUserRequest(
    String name,
    String email,
    String phone,
    UserRole role,
    String password,
    // Professional profile fields (only when role = PROFESSIONAL)
    Long professionId,
    String bio,
    BigDecimal hourlyRate,
    // Client profile fields (only when role = CLIENT)
    String taxId,
    String preferredPaymentMethod
) {}
