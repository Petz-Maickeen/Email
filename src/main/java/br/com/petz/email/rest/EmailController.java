package br.com.petz.email.rest;

import br.com.petz.email.domain.port.EmailPort;
import br.com.petz.email.rest.model.ClientEmail;
import br.com.petz.email.rest.model.DataResponse;
import br.com.petz.email.rest.model.EmailEmpty;
import br.com.petz.email.rest.util.Constantes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/cliente/{id_cliente}/email")
public class EmailController {

    @Autowired
    private EmailPort emailPort;

    @GetMapping
    private ResponseEntity<?> findClient(@Valid @PathVariable(value = "id_cliente") UUID idClient){
        List<ClientEmail> result = emailPort.findEmail(idClient);
        return result.isEmpty() ?
                ResponseEntity.status(404).body(DataResponse.builder().data(Constantes.EMAIL_NAO_ENCONTRADO).build()):
                ResponseEntity.ok().body(DataResponse.builder().data(result).build());
    }

    @PatchMapping("/{email}")
    private ResponseEntity<?> updateClient(@Valid @PathVariable(value = "id_cliente")UUID idClient,
                                           @Valid @PathVariable(value = "email")String email,
                                           @Valid @RequestBody ClientEmail clientEmail){
        clientEmail.setIdClient(idClient);
        EmailEmpty emailEmpty = emailPort.updateClient(clientEmail, email);
        return emailEmpty instanceof ClientEmail ?
                ResponseEntity.ok().body(DataResponse.builder().data(clientEmail).build()) :
                ResponseEntity.status(404).body(DataResponse.builder().data(Constantes.EMAIL_NAO_ENCONTRADO).build());
    }

    @PostMapping
    private ResponseEntity<?> insertClient(@Valid @PathVariable(value = "id_cliente")UUID idClient,
                                           @Valid @RequestBody ClientEmail clientEmail){
        clientEmail.setIdClient(idClient);
        EmailEmpty emailEmpty = emailPort.insertClient(clientEmail);
        return emailEmpty instanceof ClientEmail ?
                ResponseEntity.status(201).body(DataResponse.builder().data(clientEmail).build()):
                ResponseEntity.status(400).body(DataResponse.builder().data(Constantes.EMAIL_JA_CADASTRADO).build());
    }

    @DeleteMapping("/{email}")
    private ResponseEntity<?> removeClient(@Valid @PathVariable(value = "id_cliente")UUID idClient,
                                           @Valid @PathVariable(value = "email") String email){
        return emailPort.deleteClientByID(idClient,email) ?
                ResponseEntity.ok().body(DataResponse.builder().data(Constantes.CLIENTE_EXCLUIDO_SUCESSO).build()):
                ResponseEntity.status(404).body(DataResponse.builder().data(Constantes.EMAIL_NAO_ENCONTRADO).build());
    }
}
