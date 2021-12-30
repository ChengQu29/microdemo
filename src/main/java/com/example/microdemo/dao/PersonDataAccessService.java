package com.example.microdemo.dao;

import com.example.microdemo.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository("postgres") //this class need to be instantiated as bean, so we can inject it into other classes
public class PersonDataAccessService implements PersonDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonDataAccessService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int insertPerson(UUID id, Person person) {
        String sql = "INSERT INTO person VALUES (?,?)";
        jdbcTemplate.update(sql, id, person.getName());
        return 0;
    }

    @Override
    public List<Person> selectAllPeople() {
        String sql = "SELECT id, name FROM person";
        List<Person> people = jdbcTemplate.query(sql, (resultSet,i) -> {
            UUID id = UUID.fromString(resultSet.getString("id"));
            String name = resultSet.getString("name");
            return new Person(id, name);
        });
        return people;
    }


    @Override
    public Optional<Person> selectPersonById(UUID id) {

        String sql = "SELECT id, name FROM person WHERE id = ?";
        Person person = jdbcTemplate.queryForObject(
                sql,
                new Object[]{id},
                (resultSet,i) -> {
                    UUID personId = UUID.fromString(resultSet.getString("id"));
                    String name = resultSet.getString("name");
                    return new Person(personId, name);
                });
        return Optional.ofNullable(person);
    }

    /*
    @Override
    public Optional<Person> selectPersonById(UUID id) {
        String sql = "SELECT id, name FROM person WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, this::mapRowToPerson, id);
    }


    private Optional<Person> mapRowToPerson(ResultSet resultSet, int rowNum) throws SQLException {
        return Person.builder()
                .id(UUID.fromString(resultSet.getString("id")))
                .name(resultSet.getString("name"))
                .build();
    }*/

    @Override
    public int deletePersonById(UUID id) { //return type should be changed to optimize

        String sql = "DELETE FROM person WHERE id = ?";
        jdbcTemplate.update(sql, id);
        return 0;
    }

    @Override
    public int updatePersonById(UUID id, Person person) {

        String sql = "UPDATE person SET " + "name = ? " + "WHERE id = ?";
        jdbcTemplate.update(sql, person.getName(), id);

        return 0;
    }
}
