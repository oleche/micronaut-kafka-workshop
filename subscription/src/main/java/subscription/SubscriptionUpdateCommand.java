package subscription;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class SubscriptionUpdateCommand {
    @NotNull
    private Long id;

    @NotBlank
    private String courseName;

    public SubscriptionUpdateCommand() {}

    public SubscriptionUpdateCommand(Long id, String courseName) {
        this.id = id;
        this.courseName = courseName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }
}
