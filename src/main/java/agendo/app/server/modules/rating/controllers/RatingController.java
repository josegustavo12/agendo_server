package agendo.app.server.modules.rating.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import agendo.app.server.modules.rating.dto.CreateRatingRequest;
import agendo.app.server.modules.rating.dto.RatingResponse;
import agendo.app.server.modules.rating.models.RatingEntity;
import agendo.app.server.modules.rating.service.RatingService;
import agendo.app.server.modules.user.models.UserEntity;
import agendo.app.server.modules.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/ratings")
@RequiredArgsConstructor
@Tag(name = "Ratings", description = "Avaliações de profissionais por clientes")
public class RatingController {

    private final RatingService ratingService;
    private final UserService userService;

    @PostMapping
    @Operation(summary = "Avaliar profissional", description = "Apenas clientes podem avaliar. Score de 1 a 5.")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Avaliação criada com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos"),
        @ApiResponse(responseCode = "403", description = "Apenas clientes podem avaliar")
    })
    public ResponseEntity<RatingResponse> create(
            @RequestBody @Valid CreateRatingRequest request,
            @AuthenticationPrincipal UserEntity client) {

        UserEntity professional = userService.findById(request.professionalId());

        RatingEntity rating = RatingEntity.builder()
                .score(request.score())
                .comment(request.comment())
                .client(client)
                .professional(professional)
                .build();

        return ResponseEntity.status(201).body(toResponse(ratingService.create(rating)));
    }

    @GetMapping("/professional/{professionalId}")
    @Operation(summary = "Listar avaliações de um profissional", description = "Público. Retorna todas as avaliações recebidas por um profissional.")
    @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso")
    public ResponseEntity<List<RatingResponse>> findByProfessional(@PathVariable Long professionalId) {
        List<RatingResponse> ratings = ratingService.findByProfessionalId(professionalId)
                .stream().map(this::toResponse).toList();
        return ResponseEntity.ok(ratings);
    }

    @GetMapping("/my-ratings")
    @Operation(summary = "Avaliações feitas pelo cliente autenticado")
    @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso")
    public ResponseEntity<List<RatingResponse>> findMyRatings(@AuthenticationPrincipal UserEntity client) {
        List<RatingResponse> ratings = ratingService.findByClient(client)
                .stream().map(this::toResponse).toList();
        return ResponseEntity.ok(ratings);
    }

    private RatingResponse toResponse(RatingEntity rating) {
        return new RatingResponse(
            rating.getId(),
            rating.getScore(),
            rating.getComment(),
            rating.getClient().getId(),
            rating.getClient().getName(),
            rating.getProfessional().getId(),
            rating.getProfessional().getName(),
            rating.getCreatedAt()
        );
    }
}
