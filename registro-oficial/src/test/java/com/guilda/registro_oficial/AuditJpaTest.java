package com.guilda.registro_oficial;

import com.guilda.registro_oficial.models.audit.OrganizacaoModel;
import com.guilda.registro_oficial.models.audit.RoleModel;
import com.guilda.registro_oficial.models.audit.UsuarioModel;
import com.guilda.registro_oficial.repositories.OrganizacaoRepository;
import com.guilda.registro_oficial.repositories.RoleRepository;
import com.guilda.registro_oficial.repositories.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.test.context.TestPropertySource;

import java.time.OffsetDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(properties = {
        "spring.datasource.url=jdbc:postgresql://localhost:5433/postgres",
        "spring.datasource.username=postgres",
        "spring.datasource.password=postgres123",
        "spring.jpa.properties.hibernate.default_schema=operacoes",
        "spring.cloud.config.enabled=false",
        "eureka.client.enabled=false"
})
class AuditJpaTest {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private OrganizacaoRepository organizacaoRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Test
    void deveCarregarUsuarioComOrganizacao() {
        UsuarioModel usuario = usuarioRepository.findById(1L).orElse(null);

        assertThat(usuario).isNotNull();
        assertThat(usuario.getOrganizacao()).isNotNull();
        assertThat(usuario.getOrganizacao().getNome()).isNotBlank();
    }

    @Test
    void deveCarregarUsuarioComRoles() {
        UsuarioModel usuario = usuarioRepository.findById(1L).orElse(null);

        assertThat(usuario).isNotNull();
        assertThat(usuario.getRoles()).isNotNull();
    }

    @Test
    void deveCarregarRoleComPermissions() {
        List<RoleModel> roles = roleRepository.findAll();

        assertThat(roles).isNotEmpty();
        roles.forEach(role -> assertThat(role.getPermissions()).isNotNull());
    }

    @Test
    void devePersistirNovoUsuario() {
        OrganizacaoModel org = organizacaoRepository.findById(1L).orElseThrow();

        UsuarioModel novo = new UsuarioModel();
        novo.setOrganizacao(org);
        novo.setNome("Teste Usuario");
        novo.setEmail("teste@teste.com");
        novo.setSenhaHash("hash_teste");
        novo.setStatus("ATIVO");
        novo.setCreatedAt(OffsetDateTime.now());
        novo.setUpdatedAt(OffsetDateTime.now());

        UsuarioModel salvo = usuarioRepository.save(novo);

        assertThat(salvo.getId()).isNotNull();
        assertThat(salvo.getOrganizacao().getId()).isEqualTo(1L);
    }
}