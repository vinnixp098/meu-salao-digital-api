package com.vinicius.meu_salao_digital_api.busines.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DisponibilidadeResponseDTO {

    private LocalDate data;

    private List<LocalTime> horariosDisponiveis;

}