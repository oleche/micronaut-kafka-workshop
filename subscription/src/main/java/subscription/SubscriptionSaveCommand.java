package subscription;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class SubscriptionSaveCommand {
    @NotNull
    private Long studentId;
    private String courseName;
    private String professor;

    public SubscriptionSaveCommand() {}

    public SubscriptionSaveCommand(Long studentId) {
        this.studentId = studentId;
    }

    public SubscriptionSaveCommand(Long studentId, String courseName, String professor) {
        this.studentId = studentId;
        this.courseName = courseName;
        this.professor = professor;
    }

    public Long getStudentId() {
        return studentId;
    }

    public String getCourseName() {
        return courseName;
    }

    public String getProfessor() {
        return professor;
    }
}
