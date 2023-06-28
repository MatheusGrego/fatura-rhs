package com.rhs.extrato.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.UUID;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "EXTRATO_RHS_CRLV" , schema = "db_fatura_prime")
public class FaturaCrlv {
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "passagem")
    private String passagem;
    @Column(name = "consulta")
    private String consulta;
    @Column(name = "usuario")
    private String usuario;
    @Column(name = "data/hora")
    private Timestamp dataHora;
    @Column(name = "cliente")
    private String cliente;
    @Column(name = "custo")
    private String custo;
    @Column(name = "unidade")
    private String unidade;
    @Column(name = "documento")
    private String documento;

}
