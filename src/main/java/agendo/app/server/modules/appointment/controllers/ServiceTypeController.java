package agendo.app.server.modules.appointment.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import agendo.app.server.modules.appointment.dto.CreateServiceTypeRequest;
import agendo.app.server.modules.appointment.models.ServiceTypeEntity;
import agendo.app.server.modules.appointment.service.ServiceTypeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/service-types")
@RequiredArgsConstructor
@Tag(name = "Service Types", description = "Tipos de serviço oferecidos pelos profissionais")
public class ServiceTypeController {

    private final ServiceTypeService serviceTypeService;

    @PostMapping
    @Operation(summary = "Criar tipo de serviço")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Tipo de serviço criado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    public ResponseEntity<ServiceTypeEntity> create(@RequestBody CreateServiceTypeRequest request) {
        ServiceTypeEntity serviceType = ServiceTypeEntity.builder()
                .name(request.name())
                .description(request.description())
                .build();
        return ResponseEntity.status(201).body(serviceTypeService.create(serviceType));
    }

    @GetMapping
    @Operation(summary = "Listar tipos de serviço")
    @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso")
    public ResponseEntity<List<ServiceTypeEntity>> findAll() {
        return ResponseEntity.ok(serviceTypeService.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar tipo de serviço por ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Tipo de serviço encontrado"),
        @ApiResponse(responseCode = "404", description = "Não encontrado")
    })
    public ResponseEntity<ServiceTypeEntity> findById(@PathVariable Long id) {
        return ResponseEntity.ok(serviceTypeService.findById(id));
    }
}
