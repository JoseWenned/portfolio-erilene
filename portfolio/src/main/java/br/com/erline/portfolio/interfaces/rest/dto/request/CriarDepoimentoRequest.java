package br.com.erline.portfolio.interfaces.rest.dto.request;

import br.com.erline.portfolio.application.dto.CriarDepoimentoInput;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CriarDepoimentoRequest(
        @NotBlank(message = "O nome é obrigatório.")
        String nome,

        @NotBlank(message = "O comentário é obrigatório.")
        String comentario,

        @NotNull(message = "A nota é obrigatória.")
        @Min(value = 1, message = "A nota deve ser no mínimo 1.")
        @Max(value = 5, message = "A nota deve ser no máximo 5.")
        Integer nota,

        String fotoUrl
) {
    public CriarDepoimentoInput toInput() {
    return new CriarDepoimentoInput(
            nome,
            comentario,
            nota,
            fotoUrl
    );
}
}