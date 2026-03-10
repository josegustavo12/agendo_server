package agendo.app.server.modules.user.dto;

public record UserResponse(
    Long id,
    String name,
    String email,
    String phone,
    String role,
    String token
) {}
