package br.com.petz.email.domain.port;

import br.com.petz.email.rest.model.ClientEmail;
import br.com.petz.email.rest.model.EmailEmpty;

import java.util.List;
import java.util.UUID;

public interface EmailPort {
    List<ClientEmail> findEmail(UUID idClient);
    EmailEmpty insertClient(ClientEmail clientEmail);
    EmailEmpty updateClient(ClientEmail clientEmail, String email);
    boolean deleteClientByID(UUID idCliente, String email);
}
