package com.desafio.elotech.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record ContactRecordDto(
        @NotBlank(message = "Nome é obrigatório") String name,
        @NotBlank(message = "Telefone é obrigatório") String phone,
        @Email(message = "Email deve ser válido", regexp = "^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$") @NotBlank String email)
{}
