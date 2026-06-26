package com.guilda.registro_oficial.repositories;

import com.guilda.registro_oficial.models.audit.OrganizacaoModel;
import com.guilda.registro_oficial.models.aventureiros.AventureiroModel;
import feign.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AventureiroRepository extends JpaRepository<AventureiroModel, Long> {

    List<AventureiroModel> findByOrganizacao(OrganizacaoModel organizacao);
    List<AventureiroModel> findByAtivo(Boolean ativo);

    @Query("SELECT a FROM AventureiroModel a WHERE " +
            "(:classe IS NULL OR CAST(a.classe AS string) = :classe) AND " +
            "(:ativo IS NULL OR a.ativo = :ativo) AND " +
            "(:nivelMinimo IS NULL OR a.nivel >= :nivelMinimo) " +
            "ORDER BY a.id ASC")
    Page<AventureiroModel> filtrar(
            @Param("classe") String classe,
            @Param("ativo") Boolean ativo,
            @Param("nivelMinimo") Integer nivelMinimo,
            Pageable pageable
    );
}
