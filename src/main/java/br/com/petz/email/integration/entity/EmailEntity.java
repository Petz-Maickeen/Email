package br.com.petz.email.integration.entity;


import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "email")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@IdClass(EmailPK.class)
public class EmailEntity {

    @Id
    @NotNull
    @Column(name = "id_cliente",columnDefinition = "BINARY(16)")
    private UUID idCliente;

    @Id
    @NotNull
    @Column(name = "email")
    private String email;

    @NotNull
    @Column(name = "principal")
    private String principal;

}
