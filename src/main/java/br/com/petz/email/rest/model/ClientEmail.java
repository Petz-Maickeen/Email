package br.com.petz.email.rest.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClientEmail extends EmailEmpty {
    @JsonProperty(value = "idClient")
    private UUID idClient;

    @JsonProperty(value = "email")
    private String email;

    @JsonProperty(value = "principal")
    private String principal;
}
