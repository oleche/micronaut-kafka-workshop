package assignation.model;

import javax.json.Json;
import javax.json.JsonObject;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.math.BigDecimal;

@Entity
@Table(name = "Student")
@XmlRootElement
public class Student {

    @Id
    @GeneratedValue
    private long id;

    public Student() {
    }

    public Student(String firstName, String lastName, String email, String phone) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
    }

    @Column
    private String firstName;

    @Column
    private String lastName;

    @Column
    private String email;

    @Column
    private String phone;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public JsonObject toJson() {
        return Json.createObjectBuilder()
                .add("id", id)
                .add("firstName", firstName)
                .add("lastName", lastName)
                .add("email", email)
                .add("price", phone)
                .build();
    }

    @Override
    public String toString() {
        return "Order [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email
                + ", price=" + phone + "]";
    }
}
