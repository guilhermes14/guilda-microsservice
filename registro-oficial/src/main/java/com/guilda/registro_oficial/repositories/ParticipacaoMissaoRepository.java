package com.guilda.registro_oficial.repositories;

import com.guilda.registro_oficial.models.aventureiros.AventureiroModel;
import com.guilda.registro_oficial.models.missoes.MissaoModel;
import com.guilda.registro_oficial.models.missoes.ParticipacaoMissaoId;
import com.guilda.registro_oficial.models.missoes.ParticipacaoMissaoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParticipacaoMissaoRepository extends JpaRepository<ParticipacaoMissaoModel, ParticipacaoMissaoId> {
    List<ParticipacaoMissaoModel> findByAventureiro(AventureiroModel aventureiro);
    List<ParticipacaoMissaoModel> findByMissao(MissaoModel missao);
}
