package br.com.petz.email.domain;

import br.com.petz.email.domain.adapter.EmailRepository;
import br.com.petz.email.domain.service.EmailMapper;
import br.com.petz.email.domain.service.EmailService;
import br.com.petz.email.integration.entity.EmailEntity;
import br.com.petz.email.rest.model.ClientEmail;
import br.com.petz.email.rest.model.EmailEmpty;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;


@RunWith(SpringRunner.class)
@SpringBootTest
public class AddressServiceTest {

    @Autowired
    private EmailService emailService;

    @MockBean
    private EmailRepository emailRepository;

    @Test
    public void shouldFindPhoneByClientID(){

        UUID idCliente = UUID.randomUUID();
        List<ClientEmail> listaPhone = new ArrayList<>();
        List<EmailEntity> listaEntity = new ArrayList<>();
        ClientEmail cliente = ClientEmail.builder()
                .idClient(idCliente)
                .email("gabriel_amaro_sp@hotmail.com.br")
                .principal("principal")
                .build();

        listaPhone.add(cliente);
        listaEntity.add(EmailMapper.modelToEntity(cliente));
        when(emailRepository.existsByIdCliente(idCliente)).thenReturn(true);
        when(emailRepository.findAllByIdCliente(idCliente)).thenReturn(listaEntity);

        assertEquals(emailService.findEmail(idCliente), listaPhone);
    }

    @Test
    public void shouldNotFindPhoneByClientID(){

        UUID idCliente = UUID.randomUUID();
        List<ClientEmail> listaPhone = new ArrayList<>();
        List<EmailEntity> listaEntity = new ArrayList<>();
        ClientEmail cliente = ClientEmail.builder()
                .idClient(idCliente)
                .email("gabriel_amaro_sp@hotmail.com.br")
                .principal("principal")
                .build();

        listaPhone.add(cliente);
        listaEntity.add(EmailMapper.modelToEntity(cliente));
        when(emailRepository.existsByIdCliente(idCliente)).thenReturn(false);
        when(emailRepository.findAllByIdCliente(idCliente)).thenReturn(listaEntity);

        assertTrue(emailService.findEmail(idCliente).isEmpty());
    }

    @Test
    public void shouldInserClient(){
        UUID idCliente = UUID.randomUUID();
        ClientEmail clienteE = ClientEmail.builder()
                .idClient(idCliente)
                .email("gabriel_amaro_sp@hotmail.com.br")
                .principal("principal")
                .build();
        ClientEmail clienteS = ClientEmail.builder()
                .idClient(idCliente)
                .email("gabriel_amaro_sp@hotmail.com.br")
                .principal("principal")
                .build();

        when(emailRepository.existsByIdClienteAndEmail(idCliente,clienteE.getEmail())).thenReturn(false);
        when(emailRepository.save(EmailMapper.modelToEntity(clienteE))).thenReturn(EmailMapper.modelToEntity(clienteS));

        assertEquals(emailService.insertClient(clienteE),clienteS);
    }

    @Test
    public void shouldNotInserExistingPhone(){
        UUID idCliente = UUID.randomUUID();
        ClientEmail clienteE = ClientEmail.builder()
                .idClient(idCliente)
                .email("gabriel_amaro_sp@hotmail.com.br")
                .principal("principal")
                .build();

        when(emailRepository.existsByIdClienteAndEmail(idCliente, clienteE.getEmail())).thenReturn(true);
        assertTrue(emailService.insertClient(clienteE) instanceof EmailEmpty);
    }

    @Test
    public void shouldUpdateClient(){
        UUID idCliente = UUID.randomUUID();
        ClientEmail clienteE = ClientEmail.builder()
                .idClient(idCliente)
                .email("gabriel_amaro_sp@hotmail.com.br")
                .principal("principal")
                .build();

        when(emailRepository.existsByIdClienteAndEmail(idCliente,clienteE.getEmail())).thenReturn(true);
        when(emailRepository.save(EmailMapper.modelToEntity(clienteE))).thenReturn(EmailMapper.modelToEntity(clienteE));

        assertEquals(emailService.updateClient(clienteE,clienteE.getEmail()),clienteE);
    }

    @Test
    public void shouldNotUpdateInvalidPhone(){
        UUID idCliente = UUID.randomUUID();
        ClientEmail clienteE = ClientEmail.builder()
                .idClient(idCliente)
                .email("gabriel_amaro_sp@hotmail.com.br")
                .principal("principal")
                .build();

        when(emailRepository.existsByIdClienteAndEmail(idCliente,clienteE.getEmail())).thenReturn(false);

        assertTrue(emailService.updateClient(clienteE,clienteE.getEmail()) instanceof EmailEmpty);
    }

    @Test
    public void shouldDeleteClient(){
        UUID idCliente = UUID.randomUUID();
        String email = "gabriel_amaro_sp@hotmail.com.br";

        when(emailRepository.existsByIdClienteAndEmail(idCliente,email)).thenReturn(true);

        assertTrue(emailService.deleteClientByID(idCliente, email));
    }

    @Test
    public void shouldNotDeleteInvalidClient(){
        UUID idCliente = UUID.randomUUID();
        String email = "gabriel_amaro_sp@hotmail.com.br";

        when(emailRepository.existsByIdClienteAndEmail(idCliente,email)).thenReturn(false);

        assertFalse(emailService.deleteClientByID(idCliente,email));
    }
}
