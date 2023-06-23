package com.rhs.extrato.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "EXTRATO_RHS" , schema = "db_fatura_prime")
public class ExtratoModel {
    @Column(name = "Documento")
    private String documento;
    @Column(name = "Login")
    private String login;
    @Column(name = "Data_consulta")
    private String dataConsulta;
    @Column(name = "Hora_consulta")
    private String horaConsulta;
    @Column(name = "Valor")
    private Double valor;
    @Column(name = "IP_Consulta")
    private String ipConsulta;
    @Column(name = "Nome_consulta")
    private String nomeConsulta;
}
