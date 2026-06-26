package com.guilda.registro_oficial.repositories;

import com.guilda.registro_oficial.models.audit.AuditEntryModel;
import com.guilda.registro_oficial.models.audit.OrganizacaoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuditEntryRepository extends JpaRepository<AuditEntryModel, Long> {
    List<AuditEntryModel> findByOrganizacao(OrganizacaoModel organizacao);
}
