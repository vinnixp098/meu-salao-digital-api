package com.vinicius.meu_salao_digital_api.busines.dto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;

@Data
@Getter
@Setter
@Builder
public class ServicoCadastroDTO {
    private String descricao;

    private BigDecimal valor;

    private BigDecimal valor_promocao;

    @NotNull(message = "Id da empresa é obrigatória")
    private Integer empresa_id;

    private Boolean promocao_ativo;
}
