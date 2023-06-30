package com.rhs.extrato.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(
                        name = "Matheus Grego",
                        email = "matheusdeleongrego@gmail.com",
                        url = "https://github.com/MatheusGrego"
                ),
                description = "Essa aplicação recebe uma planilha em um endpoint, lê os dados e salva eles em um banco de dados PostgreSQL",
                title = "Sheet Writer"
        )

)
public class OpenApiConfig {
}
