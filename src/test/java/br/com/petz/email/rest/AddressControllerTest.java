package br.com.petz.email.rest;

import br.com.petz.email.JsonUtil;
import br.com.petz.email.domain.port.EmailPort;
import br.com.petz.email.rest.model.ClientEmail;
import br.com.petz.email.rest.model.DataResponse;
import br.com.petz.email.rest.model.EmailEmpty;
import br.com.petz.email.rest.util.Constantes;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@WebMvcTest(EmailController.class)
public class AddressControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmailPort emailPort;

    @Test
    public void shouldFindClientByID() throws Exception {
        UUID idCliente = UUID.randomUUID();
        List<ClientEmail> lista = new ArrayList<>();
        ClientEmail cliente = ClientEmail.builder()
                .idClient(idCliente)
                .email("gabriel_amaro_sp@hotmail.com.br")
                .principal("principal")
                .build();
        lista.add(cliente);

        when(emailPort.findEmail(idCliente)).thenReturn(lista);
        mockMvc
                .perform(MockMvcRequestBuilders.get("/cliente/" + idCliente + "/email"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(JsonUtil.toJson(DataResponse.builder().data(lista).build())));
    }

    @Test
    public void shouldNotFindClientByID() throws Exception {
        UUID idCliente = UUID.randomUUID();
        List<ClientEmail> lista = new ArrayList<>();

        when(emailPort.findEmail(idCliente)).thenReturn(lista);
        mockMvc
                .perform(MockMvcRequestBuilders.get("/cliente/" + idCliente + "/email"))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().json(JsonUtil.toJson(DataResponse.builder().data(Constantes.EMAIL_NAO_ENCONTRADO).build())));
    }

    @Test
    public void shouldInsertClient() throws Exception {
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

        when(emailPort.insertClient(clienteS)).thenReturn(clienteS);
        mockMvc
                .perform(MockMvcRequestBuilders.post("/cliente/" + idCliente + "/email")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.toJson(clienteE)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().json(JsonUtil.toJson(DataResponse.builder().data(clienteS).build())));
    }

    @Test
    public void shouldNotInsertClient() throws Exception {
        UUID idCliente = UUID.randomUUID();
        ClientEmail clienteE = ClientEmail.builder()
                .idClient(idCliente)
                .email("gabriel_amaro_sp@hotmail.com.br")
                .principal("principal")
                .build();

        when(emailPort.insertClient(clienteE)).thenReturn(new EmailEmpty());
        mockMvc
                .perform(MockMvcRequestBuilders.post("/cliente/" + idCliente + "/email")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.toJson(clienteE)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().json(JsonUtil.toJson(DataResponse.builder().data(Constantes.EMAIL_JA_CADASTRADO).build())));
    }

    @Test
    public void shouldUpdateClient() throws Exception {
        UUID idCliente = UUID.randomUUID();
        ClientEmail clienteE = ClientEmail.builder()
                .idClient(idCliente)
                .email("gabriel_amaro_sp@hotmail.com.br")
                .principal("principal")
                .build();

        when(emailPort.updateClient(clienteE, clienteE.getEmail())).thenReturn(clienteE);
        mockMvc
                .perform(MockMvcRequestBuilders.patch("/cliente/"+idCliente+"/email/" + clienteE.getEmail())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.toJson(clienteE)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(JsonUtil.toJson(DataResponse.builder().data(clienteE).build())));
    }

    @Test
    public void shouldNotUpdateClient() throws Exception {
        UUID idCliente = UUID.randomUUID();
        ClientEmail clienteE = ClientEmail.builder()
                .idClient(idCliente)
                .email("gabriel_amaro_sp@hotmail.com.br")
                .principal("principal")
                .build();

        when(emailPort.updateClient(clienteE, clienteE.getEmail())).thenReturn(new EmailEmpty());
        mockMvc
                .perform(MockMvcRequestBuilders.patch("/cliente/"+idCliente+"/email/"+clienteE.getEmail())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.toJson(clienteE)))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().json(JsonUtil.toJson(DataResponse.builder().data(Constantes.EMAIL_NAO_ENCONTRADO).build())));
    }

    @Test
    public void shouldDeleteClient() throws Exception {
        UUID idCliente = UUID.randomUUID();
        String cep = "03111-030";
        when(emailPort.deleteClientByID(idCliente,cep)).thenReturn(true);
        mockMvc
                .perform(MockMvcRequestBuilders.delete("/cliente/" + idCliente + "/email/" + cep))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(JsonUtil.toJson(DataResponse.builder().data(Constantes.CLIENTE_EXCLUIDO_SUCESSO).build())));
    }

    @Test
    public void shouldNotDeleteClient() throws Exception {
        UUID idCliente = UUID.randomUUID();
        String email = "gabriel_amaro_sp@hotmail.com.br";
        when(emailPort.deleteClientByID(idCliente,email)).thenReturn(false);
        mockMvc
                .perform(MockMvcRequestBuilders.delete("/cliente/" + idCliente + "/email/" + email))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().json(JsonUtil.toJson(DataResponse.builder().data(Constantes.EMAIL_NAO_ENCONTRADO).build())));
    }
}