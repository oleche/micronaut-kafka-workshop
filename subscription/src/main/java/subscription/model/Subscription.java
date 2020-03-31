package subscription.model;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@Entity
@Table(name = "Subscription")
@XmlRootElement
public class Subscription {

    @Id
    @GeneratedValue
    private long id;

    @Column
    private long studentId;

    @Column
    private String courseName;

    @Column
    private String professor;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    public long getStudentId() {
        return studentId;
    }

    public void setStudentId(long studentId) {
        this.studentId = studentId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getProfessor() {
        return professor;
    }

    public void setProfessor(String professor) {
        this.professor = professor;
    }

    public Subscription(long studentId, String courseName, String professor) {
        this.studentId = studentId;
        this.courseName = courseName;
        this.professor = professor;
    }

    public JsonObject toJson() {
        return Json.createObjectBuilder()
                .add("id", id)
                .add("studentId", studentId)
                .add("courseName", courseName)
                .add("professor", professor)
                .build();
    }

    @Override
    public String toString() {
        return "Subscription{" +
                "id=" + id +
                ", studentId=" + studentId +
                ", courseName=" + courseName +
                ", professor=" + professor +
                '}';
    }
}
