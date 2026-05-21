package com.vinicius.meu_salao_digital_api.busines.repository;

import com.vinicius.meu_salao_digital_api.busines.entitys.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmpresaRepository extends JpaRepository<Empresa, Integer> {

    List<Empresa> findAllByDocument(String document);

    List<Empresa> findAllByDeleted(Boolean deleted);
}
