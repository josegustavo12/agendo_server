package agendo.app.server.modules.user.dto;

import agendo.app.server.modules.user.models.UserRole;

public record CreateUserRequest(String name, String email, String phone, UserRole role, String password) {}
