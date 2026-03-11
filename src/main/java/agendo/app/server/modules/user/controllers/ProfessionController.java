package agendo.app.server.modules.user.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import agendo.app.server.modules.user.models.ProfessionEntity;
import agendo.app.server.modules.user.repository.ProfessionRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/professions")
@RequiredArgsConstructor
@Tag(name = "Professions", description = "Consulta das profissões cadastradas")
public class ProfessionController {

    private final ProfessionRepository professionRepository;

    @GetMapping
    @Operation(summary = "Listar todas as profissões", description = "Retorna todas as profissões cadastradas com id e nome, para uso nos filtros de busca de profissionais")
    @ApiResponse(responseCode = "200", description = "Lista de profissões retornada com sucesso")
    public ResponseEntity<List<ProfessionEntity>> findAll() {
        return ResponseEntity.ok(professionRepository.findAll());
    }
}
