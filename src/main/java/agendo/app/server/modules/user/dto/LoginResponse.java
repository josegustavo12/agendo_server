package agendo.app.server.modules.user.dto;

public record LoginResponse(Long id, String name, String email, String token) {}
