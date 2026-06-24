package com.guilda.registro_oficial.repositories;

import com.guilda.registro_oficial.models.audit.OrganizacaoModel;
import com.guilda.registro_oficial.models.audit.UsuarioModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioModel, Long> {
    List<UsuarioModel> findByOrganizacao(OrganizacaoModel organizacao);
    Optional<UsuarioModel> findByEmail(String email);
}
