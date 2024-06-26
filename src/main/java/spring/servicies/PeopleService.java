package spring.servicies;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.models.Person;
import spring.repositories.PeopleRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class PeopleService {

    private final PeopleRepository peopleRepository;

    @Autowired
    public PeopleService(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

    public List<Person> findAll() {
        return peopleRepository.findAll();
    }

    public Person findOne(int id) {
        Optional<Person> person = peopleRepository.findById(id);
        return person.orElse(null);
    }


    @Transactional
    public Person findByEmail(int id, String email) {
        Optional<Person> person = peopleRepository.findByEmail(email);
        return person.orElse(null);
    }


    @Transactional
    public void save(Person person) {
        person.setCreatedAt(new Date());
        peopleRepository.save(person);
    }

    @Transactional
    public void update(int id, Person updatedPerson) {
        updatedPerson.setId(id);
        peopleRepository.save(updatedPerson);
    }

    @Transactional
    public void delete(int id) {
        peopleRepository.deleteById(id);
    }



    //    @Transactional
//    public Optional<Person> show(int id, String email) {
//        Session session = sessionFactory.getCurrentSession();
//        return  session.createQuery("select p from Person p where email = :email and id != :id", Person.class).
//                setParameter("email", email).setParameter("id", id).getResultList().stream().findAny();
//    }
}
