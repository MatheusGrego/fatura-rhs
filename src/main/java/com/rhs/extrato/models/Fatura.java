package com.rhs.extrato.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "EXTRATO_RHS" , schema = "db_fatura_prime")
public class Fatura {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;
    @Column(name = "Documento")
    private String documento;
    @Column(name = "Login")
    private String login;
    @Column(name = "Data_consulta")
    private String dataConsulta;
    @Column(name = "Hora_consulta")
    private String horaConsulta;
    @Column(name = "Valor")
    private String valor;
    @Column(name = "IP_Consulta")
    private String ipConsulta;
    @Column(name = "Nome_consulta")
    private String nomeConsulta;
}
