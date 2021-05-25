package br.com.petz.email.integration.entity;

import java.io.Serializable;
import java.util.UUID;

public class EmailPK implements Serializable {
    private UUID idCliente;
    private String email;
}
