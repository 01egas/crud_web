package spring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import spring.models.Person;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Repository
public interface PeopleRepository extends JpaRepository<Person, Integer> {

    List<Person> findByUsername(String username);

    List<Person> findByUsernameOrderByAge(String username);

    Optional<Person> findByEmail(String email);

}

