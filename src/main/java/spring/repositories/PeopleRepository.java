package spring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import spring.models.Person;

import javax.swing.text.html.Option;
import java.util.Optional;

@Repository
public interface PeopleRepository extends JpaRepository<Person, Integer> {

//    @Query("SELECT u FROM User u WHERE u.email = :email")
//    Optional<Person> findByEmail(@Param("email")String email);

}