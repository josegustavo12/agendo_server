package agendo.app.server.modules.user.dto;

import java.math.BigDecimal;

public record UserResponse(
    Long id,
    String name,
    String email,
    String phone,
    String role,
    String token,
    ProfessionalProfileResponse professionalProfile,
    ClientProfileResponse clientProfile
) {
    public record ProfessionalProfileResponse(
        Long professionId,
        String professionName,
        String bio,
        BigDecimal hourlyRate,
        Double ratingAverage,
        Boolean isAvailable
    ) {}

    public record ClientProfileResponse(
        String taxId,
        String preferredPaymentMethod
    ) {}
}
