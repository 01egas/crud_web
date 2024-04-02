package spring.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;
import spring.models.Person;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class PersonDAO {

    private final SessionFactory sessionFactory;

    @Autowired
    public PersonDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Transactional(readOnly = true)
    public List<Person> index() {
        Session session = sessionFactory.getCurrentSession();
        List<Person> list = session.createQuery("select p from Person p", Person.class).getResultList();
        return list;
    }

    @Transactional(readOnly = true )
    public Person show(int id) {
        Session session = sessionFactory.getCurrentSession();
        Person person = session.get(Person.class, id);
        return person;
    }

    @Transactional
    public Optional<Person> show(int id, String email) {
        Session session = sessionFactory.getCurrentSession();
        return  session.createQuery("select p from Person p where email = :email and id != :id", Person.class).
                setParameter("email", email).setParameter("id", id).getResultList().stream().findAny();
    }

    @Transactional
    public void save(Person person) {
        Session session = sessionFactory.getCurrentSession();
        session.save(person);
    }

    @Transactional
    public void update(int id, Person updatedPerson) {
        Session session = sessionFactory.getCurrentSession();
//        Person person = session.get(Person.class, id);
//        person.setUsername(updatedPerson.getUsername());
//        person.setAge(updatedPerson.getAge());
//        person.setEmail(updatedPerson.getEmail());
//        person.setAddress(updatedPerson.getAddress());
        session.update(updatedPerson);
    }

    @Transactional
    public void delete(int id) {
        Session session = sessionFactory.getCurrentSession();
        Person person = session.get(Person.class, id);
        session.delete(person);
    }



    ///////////////////////////
    //testing batch
    ///////////////////////////

//    private List<Person> create1000people() {
//        List<Person> people = new ArrayList<>();
//
//        for (int i = 0; i < 1000; i++) {
//            people.add(new Person(i, "name" + i, 30 + i, "name" + i + "@mail.ru",
//                    "address" + i));
//        }
//
//        return people;
//    }
//
//
//    public void testMultipleUpdate() {
//        List<Person> people = create1000people();
//        long timeBefore = System.currentTimeMillis();
//
//        for (Person person : people) {
//            jdbcTemplate.update("INSERT INTO person VALUES(?, ?, ?, ?)",
//                    person.getId(), person.getUsername(), person.getAge(), person.getEmail());
//        }
//
//        long timeAfter = System.currentTimeMillis();
//        System.out.println("time = " + (timeAfter - timeBefore));
//    }
//
//    public void testBatchUpdate() {
//        List<Person> people = create1000people();
//        long timeBefore = System.currentTimeMillis();
//
//        jdbcTemplate.batchUpdate("INSERT INTO person VALUES(?, ?, ?, ?)", new BatchPreparedStatementSetter() {
//            @Override
//            public void setValues(PreparedStatement ps, int i) throws SQLException {
//                ps.setInt(1, people.get(i).getId());
//                ps.setString(2, people.get(i).getUsername());
//                ps.setInt(3, people.get(i).getAge());
//                ps.setString(4, people.get(i).getEmail());
//            }
//
//            @Override
//            public int getBatchSize() {
//                return people.size();
//            }
//        });
//
//        long timeAfter = System.currentTimeMillis();
//        System.out.println("time = " + (timeAfter - timeBefore));
//    }
}
