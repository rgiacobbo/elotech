package com.desafio.elotech.controllers;

import com.desafio.elotech.dtos.PersonRecordDto;
import com.desafio.elotech.mapper.DtoMapper;
import com.desafio.elotech.models.PersonModel;
import com.desafio.elotech.repositories.IPersonRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/persons")
public class PersonController {

    @Autowired
    IPersonRepository personRepository;

    @GetMapping("/")
    public ResponseEntity<Page<PersonModel>> getAllPersons(Pageable pageable) {
        Page<PersonModel> persons = personRepository.findAll(pageable);
        return ResponseEntity.ok(persons);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Optional<PersonModel>> getPersonById(@PathVariable UUID id) {
        Optional<PersonModel> person = personRepository.findById(id);
        if (person.isPresent()) {
            return ResponseEntity.ok(person);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/")
    public ResponseEntity<PersonRecordDto> createPerson(@RequestBody @Valid PersonRecordDto personRecordDto) {
        var personModel = DtoMapper.mapDtoToEntity(personRecordDto);

        var personDto = DtoMapper.mapEntityToDto(personRepository.save(personModel));
        return ResponseEntity.status(HttpStatus.CREATED).body(personDto);

    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updatePerson(@PathVariable UUID id, @RequestBody @Valid PersonRecordDto personRecordDto) {
        var person = this.personRepository.findById(id).orElse(null);

        if (person == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Pessoa não encontrada");
        } else {
            person.setName(personRecordDto.name());
            person.setCpf(personRecordDto.cpf());
            person.setBirthDate(personRecordDto.birthDate());
            personRepository.save(person);
            return ResponseEntity.status(HttpStatus.OK).body("Os dados foram alterados.");
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePerson(@PathVariable UUID id) {
        if (personRepository.existsById(id)) {
            personRepository.deleteById(id);
            return ResponseEntity.noContent().build();

        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
