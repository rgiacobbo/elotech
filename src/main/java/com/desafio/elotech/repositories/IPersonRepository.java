package com.desafio.elotech.repositories;

import com.desafio.elotech.models.PersonModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface IPersonRepository extends JpaRepository<PersonModel, UUID> {

}
