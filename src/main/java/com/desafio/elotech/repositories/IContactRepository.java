package com.desafio.elotech.repositories;

import com.desafio.elotech.models.ContactModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IContactRepository extends JpaRepository<ContactModel, UUID> {
}
