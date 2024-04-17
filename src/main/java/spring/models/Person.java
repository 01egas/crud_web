package spring.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Entity
@Table(name = "person")
public class Person {
   @Id
   @Column(name = "id")
   @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

   @Column(name = "username")
   @NotEmpty(message = "Name should not be empty!")
    @Size(min = 2, max = 30, message = "Name should be between 2 and 30 characters!")
    private String username;


   @Column(name = "age")
    @Min(value = 0, message = "Age should be greater than zero!")
    private int age;

   @Column(name = "email")
    @NotEmpty(message = "email should not be empty!")
    @Email(message = "email should be valid")
    private String email;

   @Column(name = "address")
    @Pattern(regexp = "[A-Z]\\w+, [A-Z]\\w+, \\d{6}", message = "Your address should be in this format: Country, City, postal code(6 digits)")
    private String address;


   @Column(name = "date_of_birth")
   @Temporal(TemporalType.DATE)
//   @Pattern(regexp = "\\d{2}.\\d{2}.\\d{4}", message = "format dd.MM.yyyy")
   @DateTimeFormat(pattern = "dd.MM.yyyy")
   private Date dateOfBirth;

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    @Column(name = "created_at")
   @Temporal(TemporalType.TIMESTAMP)
   private Date createdAt;


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Person(int id, String username, int age, String email, String address) {
        this.username = username;
        this.id = id;
        this.age = age;
        this.email = email;
        this.address = address;
    }

    public Person() {

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
