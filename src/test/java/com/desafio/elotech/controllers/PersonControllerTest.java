package com.desafio.elotech.controllers;


import com.desafio.elotech.models.ContactModel;
import com.desafio.elotech.models.PersonModel;
import com.desafio.elotech.repositories.IPersonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
class PersonControllerTest {

    @Mock
    IPersonRepository personRepository;
    
    
    private PersonController personController;


    @BeforeEach
    void setup(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @DisplayName("Criação do usuario bem sucedida")
    void createPerson() {
        PersonModel person = new PersonModel();
        person.setId(UUID.fromString("260408af-6de8-43af-8188-1cfcd93a05f7"));
        person.setName("Ricardo");
        person.setBirthDate(LocalDate.parse("2023-01-01"));

        List<ContactModel> contacts = new ArrayList<>();
        ContactModel contact = new ContactModel();
        contact.setName("Contato1");
        contact.setPhone("9999-9999");
        contact.setEmail("ricardo@gmail.com");
        contacts.add(contact);

        person.setContactModels(contacts);

        when(personRepository.findById(UUID.fromString("260408af-6de8-43af-8188-1cfcd93a05f7"))).thenReturn(Optional.of(person));

        verify(personRepository, times(1)).save(person);

    }

    @Test
    @DisplayName("Recuperação de todas as pessoas bem-sucedida")
    void getAllPersons() {
        // Crie dados fictícios de pessoas
        PersonModel person = new PersonModel();
        person.setId(UUID.fromString("260408af-6de8-43af-8188-1cfcd93a05f7"));
        person.setName("Ricardo");
        person.setBirthDate(LocalDate.parse("2023-01-01"));

        List<ContactModel> contacts = new ArrayList<>();
        ContactModel contact = new ContactModel();
        contact.setName("Contato1");
        contact.setPhone("9999-9999");
        contact.setEmail("ricardo@gmail.com");
        contacts.add(contact);

        PersonModel person2 = new PersonModel();
        person2.setId(UUID.fromString("260408af-6de8-43af-8188-1cfcd93a05f7"));
        person2.setName("Ricardo");
        person2.setBirthDate(LocalDate.parse("2023-01-01"));

        List<ContactModel> contacts2 = new ArrayList<>();
        ContactModel contact2 = new ContactModel();
        contact2.setName("Contato1");
        contact2.setPhone("9999-9999");
        contact2.setEmail("ricardo@gmail.com");
        contacts2.add(contact2);

        person2.setContactModels(contacts2);

        List<PersonModel> people = new ArrayList<>();
        people.add(person);
        people.add(person2);
        // Simule o comportamento do personRepository.findAll() para retornar a lista de pessoas
        when(personRepository.findAll(any(Pageable.class))).thenReturn(new PageImpl<>(people));

        // Chame o método getAllPersons no controlador
        ResponseEntity<Page<PersonModel>> response = personController.getAllPersons(Mockito.mock(Pageable.class));

        // Verifique se a resposta é bem-sucedida
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void getPersonById_PersonNotFound() {
        PersonModel person = new PersonModel();
        person.setId(UUID.fromString("260408af-6de8-43af-8188-1cfcd93a05f7"));
        person.setName("Ricardo");
        person.setBirthDate(LocalDate.parse("2023-01-01"));

        List<ContactModel> contacts = new ArrayList<>();
        ContactModel contact = new ContactModel();
        contact.setName("Contato1");
        contact.setPhone("9999-9999");
        contact.setEmail("ricardo@gmail.com");
        contacts.add(contact);

        person.setContactModels(contacts);

        when(personRepository.findById(UUID.fromString("260408af-6de8-43af-8188-1cfcd93a05f7"))).thenReturn(Optional.of(person));
        verify(personRepository, times(1)).save(person);

        }


    @Test
    @DisplayName("Deve chamar métodos corretos ao excluir")
    void deletePerson() {
        PersonModel person = new PersonModel();
        person.setId(UUID.fromString("260408af-6de8-43af-8188-1cfcd93a05f7"));
        person.setName("Ricardo");
        person.setBirthDate(LocalDate.parse("2023-01-01"));

        List<ContactModel> contacts = new ArrayList<>();
        ContactModel contact = new ContactModel();
        contact.setName("Contato1");
        contact.setPhone("9999-9999");
        contact.setEmail("ricardo@gmail.com");
        contacts.add(contact);

        person.setContactModels(contacts);
        personRepository.deleteById(person.getId());
        verify(personRepository, times(1)).deleteById(person.getId());
    }

}