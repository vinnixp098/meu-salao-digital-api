package com.vinicius.meu_salao_digital_api.busines.service;

import com.vinicius.meu_salao_digital_api.busines.dto.DisponibilidadeResponseDTO;
import com.vinicius.meu_salao_digital_api.busines.entitys.Atendimento;
import com.vinicius.meu_salao_digital_api.busines.entitys.Empresa;
import com.vinicius.meu_salao_digital_api.busines.repository.AtendimentoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DisponibilidadeService {

    private final AtendimentoRepository atendimentoRepository;

    private static final List<LocalTime> HORARIOS = List.of(
            LocalTime.of(8, 0),
            LocalTime.of(9, 0),
            LocalTime.of(10, 0),
            LocalTime.of(11, 0),
            LocalTime.of(13, 0),
            LocalTime.of(14, 0),
            LocalTime.of(15, 0),
            LocalTime.of(16, 0),
            LocalTime.of(17, 0)
    );

    public List<DisponibilidadeResponseDTO> listarDisponibilidade(Integer empresa) {

        LocalDate hoje = LocalDate.now();
        LocalDate fimPeriodo = hoje.plusDays(6);

        LocalDateTime inicio = hoje.atStartOfDay();
        LocalDateTime fim = fimPeriodo.atTime(23, 59, 59);

        List<Atendimento> atendimentos =
                atendimentoRepository.buscarTodosPorPeriodo(
                        empresa,
                        inicio,
                        fim
                );

        Map<LocalDate, List<Atendimento>> atendimentosPorDia =
                atendimentos.stream()
                        .collect(Collectors.groupingBy(
                                atendimento -> atendimento.getDataAgendamento().toLocalDate()
                        ));

        List<DisponibilidadeResponseDTO> response = new ArrayList<>();

        for (int i = 0; i < 7; i++) {

            LocalDate data = hoje.plusDays(i);

            List<LocalTime> horariosDisponiveis = new ArrayList<>(HORARIOS);

            List<Atendimento> atendimentosDoDia =
                    atendimentosPorDia.getOrDefault(data, Collections.emptyList());

            // Remove horários ocupados
            for (Atendimento atendimento : atendimentosDoDia) {
                horariosDisponiveis.remove(
                        atendimento.getDataAgendamento().toLocalTime()
                );
            }

            // Remove horários passados do dia atual
            if (data.equals(hoje)) {

                LocalTime agora = LocalTime.now();

                horariosDisponiveis.removeIf(horario ->
                        !horario.isAfter(agora)
                );
            }

            // Ignora dias lotados
            if (horariosDisponiveis.isEmpty()) {
                continue;
            }

            response.add(
                    DisponibilidadeResponseDTO.builder()
                            .data(data)
                            .horariosDisponiveis(horariosDisponiveis)
                            .build()
            );
        }

        return response;
    }
}