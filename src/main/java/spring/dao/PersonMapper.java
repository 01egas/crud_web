package spring.dao;

import org.springframework.jdbc.core.RowMapper;
import spring.models.Person;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PersonMapper implements RowMapper<Person> {
    @Override
    public Person mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        Person person = new Person();
        person.setId(resultSet.getInt("id"));
        person.setAge(resultSet.getInt("age"));
        person.setUsername(resultSet.getString("username"));
        person.setEmail(resultSet.getString("email"));
        return person;
    }
}
