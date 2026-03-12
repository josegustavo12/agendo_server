package agendo.app.server.modules.rating.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record CreateRatingRequest(
    @NotNull(message = "ID do profissional é obrigatório")
    Long professionalId,

    @NotNull(message = "Score é obrigatório")
    @Min(value = 1, message = "Score deve ser no mínimo 1")
    @Max(value = 5, message = "Score deve ser no máximo 5")
    Integer score,

    String comment
) {}
