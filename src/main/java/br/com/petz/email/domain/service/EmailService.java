package br.com.petz.email.domain.service;

import br.com.petz.email.domain.adapter.EmailRepository;
import br.com.petz.email.domain.port.EmailPort;
import br.com.petz.email.integration.entity.EmailEntity;
import br.com.petz.email.rest.model.ClientEmail;
import br.com.petz.email.rest.model.EmailEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class EmailService implements EmailPort {
    @Autowired
    private EmailRepository emailRepository;
    @Override
    public List<ClientEmail> findEmail(UUID idClient) {
        List<ClientEmail> result = new ArrayList<>();
        if(emailRepository.existsByIdCliente(idClient)){
            List<EmailEntity> aux = emailRepository.findAllByIdCliente(idClient);
            for(EmailEntity entity: aux){
                result.add(EmailMapper.entityToModel(entity));
            }
        }
        return result;
    }

    @Override
    public EmailEmpty insertClient(ClientEmail clientEmail) {
        if(emailRepository.existsByIdClienteAndEmail(clientEmail.getIdClient(),clientEmail.getEmail())){
            return new EmailEmpty();
        } else {
            return EmailMapper.entityToModel(emailRepository.save(EmailMapper.modelToEntity(clientEmail)));
        }
    }

    @Override
    public EmailEmpty updateClient(ClientEmail clientEmail, String email) {
        if(emailRepository.existsByIdClienteAndEmail(clientEmail.getIdClient(),email)){
            emailRepository.delete(emailRepository.findByIdClienteAndEmail(clientEmail.getIdClient(), email));
            return EmailMapper.entityToModel(emailRepository.save(EmailMapper.modelToEntity(clientEmail)));
        } else {
            return new EmailEmpty();
        }
    }

    @Override
    public boolean deleteClientByID(UUID idCliente, String email) {
        if(emailRepository.existsByIdClienteAndEmail(idCliente,email)){
            emailRepository.delete(emailRepository.findByIdClienteAndEmail(idCliente,email));
            return true;
        } else {
            return false;
        }
    }
}
