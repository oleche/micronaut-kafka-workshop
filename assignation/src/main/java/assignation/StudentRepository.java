package assignation;

import assignation.model.Student;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface StudentRepository {
    Optional<Student> findById(@NotNull Long id);

    Student save(@NotBlank String name, String lastname, @NotBlank String email, @NotBlank String phone);

    void deleteById(@NotNull Long id);

    List<Student> findAll(@NotNull SortingAndOrderArguments args);

    List<Student> update(@NotNull Long id, @NotBlank String name);
}
