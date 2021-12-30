package com.example.microdemo.dao;

import ch.qos.logback.classic.db.names.DBNameResolver;
import com.example.microdemo.model.Person;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository("fakeDao") //this class need to be instantiated as bean, so we can inject it into other classes
public class FakePersonDataAccessService implements PersonDao {

    private static List<Person> DB = new ArrayList<>();

    @Override
    public int insertPerson(UUID id, Person person){
        DB.add(new Person(id, person.getName()));
        return 1; //insertion always works
    }

    @Override
    public List<Person> selectAllPeople(){
        return DB;
    }

    @Override
    public Optional<Person> selectPersonById(UUID id) { //Optional is a container object which may or may not contain a non-null value
        return DB.stream()
                .filter(person -> person.getId().equals(id))
                .findFirst();
    }

    @Override
    public int deletePersonById(UUID id) {
        Optional<Person> personMaybe = selectPersonById(id);
        if (personMaybe.isEmpty()) {
            return 0;
        }
        DB.remove(personMaybe.get());
        return 1;
    }

    @Override
    public int updatePersonById(UUID id, Person update) {
        return selectPersonById(id)
                .map(person -> {
                    int indexOfPersonToUpdate = DB.indexOf(person);
                    if (indexOfPersonToUpdate >= 0) {
                        DB.set(indexOfPersonToUpdate, new Person(id, update.getName()));
                        return 1;
                    }
                    return 0;
                })
                .orElse(0);
    }
}
