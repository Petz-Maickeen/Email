package br.com.petz.email.domain.service;

import br.com.petz.email.integration.entity.EmailEntity;
import br.com.petz.email.rest.model.ClientEmail;
import br.com.petz.email.rest.model.EmailEmpty;

public class EmailMapper {
    public static ClientEmail entityToModel(EmailEntity entity){
        return ClientEmail.builder()
                .idClient(entity.getIdCliente())
                .email(entity.getEmail())
                .principal(entity.getPrincipal())
                .build();
    }

    public static EmailEntity modelToEntity (ClientEmail clientEmail){
        return EmailEntity.builder()
                .idCliente(clientEmail.getIdClient())
                .email(clientEmail.getEmail())
                .principal(clientEmail.getPrincipal())
                .build();
    }
}
