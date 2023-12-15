package spring.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import spring.models.Person;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class PersonDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Person> index() {
        return jdbcTemplate.query("SELECT * FROM person", new BeanPropertyRowMapper<>(Person.class));
    }

    public Person show(int id) {
        return jdbcTemplate.query("SELECT * FROM person WHERE id=?",
                        new Object[]{id}, new BeanPropertyRowMapper<>(Person.class))
                .stream().findAny().orElse(null);
    }

    public void save(Person person) {
        jdbcTemplate.update("insert into person(username, age, email) values (?, ?, ?)",
                person.getUsername(), person.getAge(), person.getEmail());
    }

    public void update(int id, Person updatedPerson) {
        jdbcTemplate.update("UPDATE person SET username=?, age=?, email=? WHERE id=?",
                updatedPerson.getUsername(), updatedPerson.getAge(), updatedPerson.getEmail(), id);
    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM person WHERE id=?", id);
    }



    ///////////////////////////
    //testing batch
    ///////////////////////////

    private List<Person> create1000people() {
        List<Person> people = new ArrayList<>();

        for (int i = 0; i < 1000; i++) {
            people.add(new Person(i, "name" + i, 30 + i, "name" + i + "@mail.ru"));
        }
        
        return people;
    }


    public void testMultipleUpdate() {
        List<Person> people = create1000people();
        long timeBefore = System.currentTimeMillis();

        for (Person person : people) {
            jdbcTemplate.update("INSERT INTO person VALUES(?, ?, ?, ?)",
                    person.getId(), person.getUsername(), person.getAge(), person.getEmail());
        }

        long timeAfter = System.currentTimeMillis();
        System.out.println("time = " + (timeAfter - timeBefore));
    }

    public void testBatchUpdate() {
        List<Person> people = create1000people();
        long timeBefore = System.currentTimeMillis();

        jdbcTemplate.batchUpdate("INSERT INTO person VALUES(?, ?, ?, ?)", new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setInt(1, people.get(i).getId());
                ps.setString(2, people.get(i).getUsername());
                ps.setInt(3, people.get(i).getAge());
                ps.setString(4, people.get(i).getEmail());
            }

            @Override
            public int getBatchSize() {
                return people.size();
            }
        });

        long timeAfter = System.currentTimeMillis();
        System.out.println("time = " + (timeAfter - timeBefore));
    }
}
