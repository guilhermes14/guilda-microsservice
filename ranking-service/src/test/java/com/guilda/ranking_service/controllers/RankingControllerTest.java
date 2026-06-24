package com.guilda.ranking_service.controllers;

import com.guilda.ranking_service.models.RankingEntryModel;
import com.guilda.ranking_service.services.RankingService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import java.util.List;


@WebMvcTest(RankingController.class)
class RankingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private RankingService rankingService;

    @Test
    void deveRetornarRankingVazio() throws Exception {
        when(rankingService.listarRanking()).thenReturn(List.of());

        mockMvc.perform(get("/ranking"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    @Test
    void deveRetornarRankingComEntradas() throws Exception {
        RankingEntryModel entry = new RankingEntryModel();
        entry.setAventureiroId(1L);
        entry.setPontos(10);
        entry.setMissoesConcluidas(1);

        when(rankingService.listarRanking()).thenReturn(List.of(entry));

        mockMvc.perform(get("/ranking"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].aventureiroId").value(1))
                .andExpect(jsonPath("$[0].pontos").value(10));
    }

    @Test
    void devePontuarAventureiro() throws Exception {
        RankingEntryModel entry = new RankingEntryModel();
        entry.setAventureiroId(1L);
        entry.setPontos(10);
        entry.setMissoesConcluidas(1);

        when(rankingService.pontuar(1L, 10)).thenReturn(entry);

        mockMvc.perform(post("/ranking/pontuar")
                        .param("aventureiroId", "1")
                        .param("pontos", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.pontos").value(10));
    }
}
