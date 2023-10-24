package com.desafio.elotech.dtos;


import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Past;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record PersonRecordDto(
        UUID id,
        @NotBlank(message = "Nome é obrigatório") String name,
        @CPF(message = "CPF deve ser válido") String cpf,
        @Past(message = "Data de nascimento é obrigatória") LocalDate birthDate,
        @NotEmpty(message = "Deve possuir ao menos um contato")
        @Valid List<ContactRecordDto> contactRecordDto){
}
