package br.com.petz.email.domain.adapter;

import br.com.petz.email.integration.entity.EmailEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface EmailRepository extends JpaRepository<EmailEntity, String> {
    boolean existsByIdClienteAndEmail(UUID idClient, String email);
    boolean existsByIdCliente(UUID idClient);
    List<EmailEntity> findAllByIdCliente(UUID idClient);
    EmailEntity findByIdClienteAndEmail(UUID idCliente, String email);
}
