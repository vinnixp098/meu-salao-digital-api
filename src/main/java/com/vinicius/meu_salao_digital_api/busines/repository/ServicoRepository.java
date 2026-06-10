package com.vinicius.meu_salao_digital_api.busines.repository;

import com.vinicius.meu_salao_digital_api.busines.entitys.Servico;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface ServicoRepository extends JpaRepository<Servico, Integer> {


   List<Servico> findAllByEmpresaIdAndDescricao(@NotNull(message = "Id da empresa é obrigatória") Integer empresaId, String descricao);

   List<Servico> findAllByEmpresaIdAndAtivo(Integer empresa, Boolean ativo);
}
