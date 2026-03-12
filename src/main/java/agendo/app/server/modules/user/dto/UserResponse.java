package agendo.app.server.modules.user.dto;

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
        Boolean isAvailable
    ) {}

    public record ClientProfileResponse(
        String taxId,
        String preferredPaymentMethod
    ) {}
}
