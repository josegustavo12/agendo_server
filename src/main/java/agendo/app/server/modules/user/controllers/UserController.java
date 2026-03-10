package agendo.app.server.modules.user.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import agendo.app.server.modules.user.dto.CreateUserRequest;
import agendo.app.server.modules.user.dto.LoginRequest;
import agendo.app.server.modules.user.dto.LoginResponse;
import agendo.app.server.modules.user.dto.UserResponse;
import agendo.app.server.modules.user.models.UserEntity;
import agendo.app.server.modules.user.models.UserRole;
import agendo.app.server.modules.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Tag(name = "Users", description = "Gerenciamento de usuários (profissionais e clientes)")
public class UserController {

    private final UserService userService;

    @PostMapping
    @Operation(summary = "Criar usuário", description = "Cria um novo usuário com role PROFESSIONAL ou CLIENT")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Usuário criado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    public ResponseEntity<UserResponse> create(@RequestBody CreateUserRequest request) {
        UserEntity user = UserEntity.builder()
                .name(request.name())
                .email(request.email())
                .phone(request.phone())
                .role(request.role())
                .passwordHash(userService.encodePassword(request.password()))
                .build();
        UserEntity createdUser = userService.create(user);
        UserResponse response = new UserResponse(
            createdUser.getId(),
            createdUser.getName(),
            createdUser.getEmail(),
            createdUser.getPhone(),
            createdUser.getRole().name(),
            createdUser.getToken()
        );
        return ResponseEntity.status(201).body(response);
    }

    @PostMapping("/login")
    @Operation(summary = "Login", description = "Faz login com email e senha, retorna o token")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Login realizado com sucesso"),
        @ApiResponse(responseCode = "401", description = "Email ou senha inválidos")
    })
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        UserEntity user = userService.findByEmail(request.email())
                .orElseThrow(() -> new RuntimeException("Email ou senha inválidos"));

        if (!userService.validatePassword(user, request.password())) {
            throw new RuntimeException("Email ou senha inválidos");
        }

        LoginResponse response = new LoginResponse(
            user.getId(),
            user.getName(),
            user.getEmail(),
            user.getToken()
        );
        return ResponseEntity.ok(response);
    }

    @GetMapping
    @Operation(summary = "Listar usuários", description = "Retorna todos os usuários, com filtro opcional por role")
    @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso")
    public ResponseEntity<List<UserEntity>> findAll(@RequestParam(required = false) UserRole role) {
        if (role != null) return ResponseEntity.ok(userService.findByRole(role));
        return ResponseEntity.ok(userService.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar usuário por ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Usuário encontrado"),
        @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    public ResponseEntity<UserEntity> findById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.findById(id));
    }
}
