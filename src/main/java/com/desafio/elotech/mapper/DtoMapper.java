package com.desafio.elotech.mapper;

import com.desafio.elotech.dtos.ContactRecordDto;
import com.desafio.elotech.dtos.PersonRecordDto;
import com.desafio.elotech.models.ContactModel;
import com.desafio.elotech.models.PersonModel;

import java.util.ArrayList;

public class DtoMapper {
    public static PersonModel mapDtoToEntity(PersonRecordDto source) {
        var personModel = new PersonModel();
        personModel.setName(source.name());
        personModel.setCpf(source.cpf());
        personModel.setBirthDate(source.birthDate());

        var contactList = new ArrayList<ContactModel>();
        for(var contact : source.contactRecordDto()){
            var contactModel = new ContactModel();
            contactModel.setName(contact.name());
            contactModel.setEmail(contact.email());
            contactModel.setPhone(contact.phone());
            contactList.add(contactModel);
        }

        personModel.setContactModels(contactList);
        return personModel;
    }
    public static PersonRecordDto mapEntityToDto(PersonModel source) {
        var personRecordDto = new PersonRecordDto(
                source.getId(),
                source.getName(),
                source.getCpf(),
                source.getBirthDate(),
                new ArrayList<>()
        );

        for (var contactModel : source.getContactModels()) {
            var contactRecordDto = new ContactRecordDto(
                    contactModel.getName(),
                    contactModel.getPhone(),
                    contactModel.getEmail()
            );
            personRecordDto.contactRecordDto().add(contactRecordDto);
        }

        return personRecordDto;
    }
}
