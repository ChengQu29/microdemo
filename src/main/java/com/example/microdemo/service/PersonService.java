package com.example.microdemo.service;

import com.example.microdemo.dao.PersonDao;
import com.example.microdemo.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service //@Component is the same thing
public class PersonService {

    private final PersonDao personDao; //this is not instantiated, there is no way for the constructor to know about it if not using dependency injection

    @Autowired //injecting
    public PersonService(@Qualifier("postgres") PersonDao personDao) { //Qualifer tells which implementation of the dao to use
        this.personDao = personDao;
    }

    public int addPerson(Person person){
        return personDao.insertPerson(person);
    }

    public List<Person> getAllPeople(){
        return personDao.selectAllPeople();
    }

    public Optional<Person> getPersonById(UUID id){
        return personDao.selectPersonById(id);
    }

    public int deletePerson(UUID id){
        return personDao.deletePersonById(id);
    }

    public int updatePerson(UUID id, Person newPerson){
        return personDao.updatePersonById(id, newPerson);
    }
}
