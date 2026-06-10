package com.vinicius.meu_salao_digital_api.busines.service;


import com.vinicius.meu_salao_digital_api.busines.dto.ServicoCadastroDTO;
import com.vinicius.meu_salao_digital_api.busines.entitys.Servico;
import com.vinicius.meu_salao_digital_api.busines.entitys.Usuario;
import com.vinicius.meu_salao_digital_api.busines.repository.EmpresaRepository;
import com.vinicius.meu_salao_digital_api.busines.repository.ServicoRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ServicoService {
    private final ServicoRepository servicoRepository;
    private final EmpresaRepository empresaRepository;

    public ResponseEntity<?> salvar(@Valid ServicoCadastroDTO servico){

        if(empresaRepository.findById(servico.getEmpresa_id()).isEmpty()){
            return ResponseEntity.badRequest().body("Empresa não encontrada!");
        }

        if(servicoRepository.findAllByEmpresaIdAndDescricao(servico.getEmpresa_id(), servico.getDescricao()).size() > 0){
            return ResponseEntity.badRequest().body("Serviço já cadastrado!");
        }

        Servico servicoData = Servico.builder()
                .descricao(servico.getDescricao())
                .valor(servico.getValor())
                .valor_promocao(servico.getValor_promocao())
                .empresaId(servico.getEmpresa_id())
                .ativo(true)
                .promocao_ativo(servico.getPromocao_ativo())
                .build();

        servicoRepository.save(servicoData);
        return ResponseEntity.ok("Serviço cadastrado com sucesso!");

    }

    public ResponseEntity<?> buscarTodos(Integer empresa){
        return ResponseEntity.ok(servicoRepository.findAllByEmpresaId(empresa));
    }

    public ResponseEntity<?> alterarStatusAtivo(Integer id){
        Servico servico = servicoRepository.findById(id).get();
        servico.setAtivo(!servico.getAtivo());
        servicoRepository.save(servico);
        return ResponseEntity.ok("Status do serviço alterado com sucesso!");
    }

    public ResponseEntity<?> alterarStatusPromocaoAtivo(Integer id){
        Servico servico = servicoRepository.findById(id).get();
        servico.setPromocao_ativo(!servico.getPromocao_ativo());
        servicoRepository.save(servico);
        return ResponseEntity.ok("Status da promoção alterado com sucesso!");
    }

    @Transactional
    public ResponseEntity<?> deletar(Integer id){
        servicoRepository.deleteById(id);
        return ResponseEntity.ok("Serviço deletado com sucesso!");
    }


    public ResponseEntity<?> editar(@Valid Servico servico) {
        Servico servicoData = servicoRepository.findById(servico.getId()).get();
        servicoData.setValor_promocao(servico.getValor_promocao());
        servicoData.setPromocao_ativo(servico.getPromocao_ativo());
        servicoData.setValor(servico.getValor());
        servicoData.setAtivo(servico.getAtivo());
        servicoRepository.save(servicoData);
        return ResponseEntity.ok("Dados do serviço alterados com sucesso!");
    }
}
