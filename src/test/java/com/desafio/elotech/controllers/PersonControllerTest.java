package com.desafio.elotech.controllers;


import com.desafio.elotech.dtos.ContactRecordDto;
import com.desafio.elotech.dtos.PersonRecordDto;
import com.desafio.elotech.mapper.DtoMapper;
import com.desafio.elotech.models.ContactModel;
import com.desafio.elotech.models.PersonModel;
import com.desafio.elotech.repositories.IPersonRepository;
import org.h2.command.dml.MergeUsing;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.*;

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

    @InjectMocks
    private PersonController personController;


    @BeforeEach
    void setup(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @DisplayName("Criação do usuario bem sucedida")
    void createPerson() {

        List<ContactRecordDto> contacts = new ArrayList<>();
        ContactRecordDto contact = new ContactRecordDto("Contato1", "9999-9999", "ricardo@gmail.com");
        contacts.add(contact);
        PersonRecordDto person = new PersonRecordDto(null, "Ricardo", "593.014.890-23", LocalDate.parse("2023-01-01"), contacts);

        when(personRepository.save(any())).thenReturn(DtoMapper.mapDtoToEntity(person));
        personController.createPerson(person);
        ArgumentCaptor<PersonModel> personCaptor = ArgumentCaptor.forClass(PersonModel.class);
        verify(personRepository, times(1)).save(personCaptor.capture());
        var personC = personCaptor.getValue();
        assertEquals(personC.getName(), person.name());
        assertEquals(personC.getCpf(), person.cpf());
        assertEquals(personC.getBirthDate(), person.birthDate());
        assertEquals(personC.getContactModels().size(), 1);
        assertEquals(personC.getContactModels().get(0).getName(), contact.name());
        assertEquals(personC.getContactModels().get(0).getEmail(), contact.email());
        assertEquals(personC.getContactModels().get(0).getPhone(), contact.phone());

    }

    @Test
    @DisplayName("Atualização bem sucedida")
    void updatePerson(){
        var idPerson = UUID.randomUUID();

        PersonModel person = new PersonModel();
        person.setId(idPerson);
        person.setName("Ricardo");
        person.setCpf("593.014.890-23");
        person.setBirthDate(LocalDate.parse("2023-01-01"));

        PersonRecordDto personDto = new PersonRecordDto(null, "Daniel", "115.369.550-27", LocalDate.parse("2022-02-03"),new ArrayList<>());

        when(personRepository.findById(idPerson)).thenReturn(Optional.of(person));
        personController.updatePerson(idPerson, personDto);
        ArgumentCaptor<PersonModel> personCaptor = ArgumentCaptor.forClass(PersonModel.class);
        verify(personRepository, times(1)).save(personCaptor.capture());
        var personC = personCaptor.getValue();
        assertEquals(personC.getName(), personDto.name());
        assertEquals(personC.getCpf(), personDto.cpf());
        assertEquals(personC.getBirthDate(), personDto.birthDate());

    }

    @Test
    @DisplayName("Pessoa não encontrada")
    void getPersonById_PersonNotFound() {
        Mockito.when(personRepository.findById(any())).thenReturn(Optional.empty());
        var res = personController.getPersonById(UUID.randomUUID());
        assertEquals(ResponseEntity.notFound().build(), res);

        }


    @Test
    @DisplayName("Deve returnar sucesso ao excluir")
    void deletePerson() {
        var id = UUID.randomUUID();
        Mockito.when(personRepository.existsById(id)).thenReturn(true);
        var res = personController.deletePerson(id);
        verify(personRepository, times(1)).deleteById(id);
        assertEquals(ResponseEntity.noContent().build(), res);
    }

    @Test
    @DisplayName("Deve retorna falha ao excluir")
    void deletePersonFalse() {
        var id = UUID.randomUUID();
        Mockito.when(personRepository.existsById(id)).thenReturn(false);
        var res = personController.deletePerson(id);
        verify(personRepository, never()).deleteById(id);
        assertEquals(ResponseEntity.notFound().build(), res);
    }
}