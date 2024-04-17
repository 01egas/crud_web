package spring.util;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import spring.models.Person;
import spring.servicies.PeopleService;

import java.util.regex.Pattern;

@Component
public class PersonValidator implements Validator {

    private PeopleService peopleService;

    @Autowired
    public PersonValidator(PeopleService peopleService) {
        this.peopleService = peopleService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Person person = (Person) target;
//        если мэйл принадлежит текущему пользователю не выдавать ошибку
        Person findingPerson = peopleService.findByEmail(person.getId(), person.getEmail());
        if (findingPerson != null && person.getId() != findingPerson.getId()) {
            errors.rejectValue("email", "", "this email is already taken");
        }
        String DATE_PATTERN = "^\\d{2}.\\d{2}.\\d{4}$";
        if (person.getDateOfBirth() == null || person.getDateOfBirth().toString().matches(DATE_PATTERN)) {
            errors.rejectValue("dateOfBirth", "", "format dd.MM.yyyy");
        }
    }


}
