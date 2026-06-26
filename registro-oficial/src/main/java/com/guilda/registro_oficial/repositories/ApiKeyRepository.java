package com.guilda.registro_oficial.repositories;

import com.guilda.registro_oficial.models.audit.ApiKeyModel;
import com.guilda.registro_oficial.models.audit.OrganizacaoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApiKeyRepository extends JpaRepository<ApiKeyModel, Long> {
    List<ApiKeyModel> findByOrganizacao(OrganizacaoModel organizacao);
}
