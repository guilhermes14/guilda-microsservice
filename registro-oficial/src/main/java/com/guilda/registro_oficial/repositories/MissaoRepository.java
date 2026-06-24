package com.guilda.registro_oficial.repositories;

import com.guilda.registro_oficial.models.audit.OrganizacaoModel;
import com.guilda.registro_oficial.models.missoes.MissaoModel;
import com.guilda.registro_oficial.models.missoes.enums.StatusMissaoEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MissaoRepository extends JpaRepository<MissaoModel, Long> {
    List<MissaoModel> findByOrganizacao(OrganizacaoModel organizacao);
    List<MissaoModel> findByStatus(StatusMissaoEnum status);
}