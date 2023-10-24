package com.desafio.elotech.models;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;


@Data
@Entity
@Table(name = "TB_PERSON")
public class PersonModel implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String name;
    private String cpf;
    private LocalDate birthDate;

    @OneToMany(cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<ContactModel> contactModels;

}
