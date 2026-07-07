package com.vinicius.meu_salao_digital_api.busines.service;

import com.vinicius.meu_salao_digital_api.busines.dto.EmpresaCadastroDTO;
import com.vinicius.meu_salao_digital_api.busines.entitys.Empresa;
import com.vinicius.meu_salao_digital_api.busines.repository.EmpresaRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class EmpresaService {

    private final EmpresaRepository empresaRepository;

    public ResponseEntity<?> salvarEmpresa(@Valid EmpresaCadastroDTO empresa) {

        if (!empresaRepository.findAllByDocument(empresa.getDocument()).isEmpty()){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Documento já cadastrado!");
        }

        Empresa novaEmpresa = Empresa.builder()
                .description(empresa.getDescription())
                .document(empresa.getDocument())
                .email(empresa.getEmail())
                .telephone(empresa.getTelephone())
                .deleted(false)
                .build();

        empresaRepository.save(novaEmpresa);
        return ResponseEntity.ok("Empresa cadastrada com sucesso!");
    }

    public ResponseEntity<?> buscarEmpresas(@Valid Integer empresaId){

        if(empresaId == null){
            return ResponseEntity.ok(empresaRepository.findAllByDeleted(false));
        }
        return ResponseEntity.ok(empresaRepository.findAllById(Collections.singleton(empresaId)));
    }

    public ResponseEntity<?> deletar(@Valid Integer id){
        Empresa empresa = empresaRepository.findById(id).get();
        empresa.setDeleted(true);
        empresaRepository.save(empresa);
        return ResponseEntity.ok("Empresa deletada com sucesso!");
    }

}
