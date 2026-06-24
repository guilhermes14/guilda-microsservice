package com.guilda.registro_oficial.repositories;

import com.guilda.registro_oficial.models.audit.OrganizacaoModel;
import com.guilda.registro_oficial.models.aventureiros.AventureiroModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AventureiroRepository extends JpaRepository<AventureiroModel, Long> {
    List<AventureiroModel> findByOrganizacao(OrganizacaoModel organizacao);
    List<AventureiroModel> findByAtivo(Boolean ativo);
}
