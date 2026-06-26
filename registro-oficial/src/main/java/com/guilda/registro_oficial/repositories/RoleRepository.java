package com.guilda.registro_oficial.repositories;

import com.guilda.registro_oficial.models.audit.OrganizacaoModel;
import com.guilda.registro_oficial.models.audit.RoleModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<RoleModel, Long> {
    List<RoleModel> findByOrganizacao(OrganizacaoModel organizacao);
}
