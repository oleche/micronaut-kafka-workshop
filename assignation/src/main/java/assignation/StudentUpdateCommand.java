package assignation;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class StudentUpdateCommand {
    @NotNull
    private Long id;

    @NotBlank
    private String name;

    public StudentUpdateCommand() {}

    public StudentUpdateCommand(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
