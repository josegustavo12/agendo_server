package agendo.app.server.modules.appointment.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import agendo.app.server.modules.appointment.dto.CreateAppointmentRequest;
import agendo.app.server.modules.appointment.models.AppointmentEntity;
import agendo.app.server.modules.appointment.models.ServiceTypeEntity;
import agendo.app.server.modules.appointment.service.AppointmentService;
import agendo.app.server.modules.appointment.service.ServiceTypeService;
import agendo.app.server.modules.user.models.UserEntity;
import agendo.app.server.modules.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/appointments")
@RequiredArgsConstructor
@Tag(name = "Appointments", description = "Gerenciamento de agendamentos")
public class AppointmentController {

    private final AppointmentService appointmentService;
    private final ServiceTypeService serviceTypeService;
    private final UserService userService;

    @PostMapping
    @Operation(summary = "Criar agendamento", description = "Cria um agendamento vinculando um profissional, um cliente e um tipo de serviço")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Agendamento criado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos"),
        @ApiResponse(responseCode = "404", description = "Profissional, cliente ou tipo de serviço não encontrado")
    })
    public ResponseEntity<AppointmentEntity> create(@RequestBody CreateAppointmentRequest request) {
        ServiceTypeEntity serviceType = serviceTypeService.findById(request.serviceTypeId());
        UserEntity professional = userService.findById(request.professionalId());
        UserEntity client = userService.findById(request.clientId());

        AppointmentEntity appointment = AppointmentEntity.builder()
                .serviceType(serviceType)
                .professional(professional)
                .client(client)
                .valueInCents(request.valueInCents())
                .scheduleDate(request.scheduleDate())
                .build();

        return ResponseEntity.status(201).body(appointmentService.create(appointment));
    }

    @GetMapping
    @Operation(
        summary = "Listar agendamentos",
        description = "Retorna todos os agendamentos. Filtre por `professionalId` ou `clientId`"
    )
    @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso")
    public ResponseEntity<List<AppointmentEntity>> findAll(
            @Parameter(description = "ID do profissional") @RequestParam(required = false) Long professionalId,
            @Parameter(description = "ID do cliente") @RequestParam(required = false) Long clientId) {
        if (professionalId != null) return ResponseEntity.ok(appointmentService.findByProfessional(professionalId));
        if (clientId != null) return ResponseEntity.ok(appointmentService.findByClient(clientId));
        return ResponseEntity.ok(appointmentService.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar agendamento por ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Agendamento encontrado"),
        @ApiResponse(responseCode = "404", description = "Agendamento não encontrado")
    })
    public ResponseEntity<AppointmentEntity> findById(@PathVariable Long id) {
        return ResponseEntity.ok(appointmentService.findById(id));
    }
}
