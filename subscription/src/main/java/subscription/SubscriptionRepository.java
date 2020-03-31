package subscription;

import subscription.model.Subscription;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

public interface SubscriptionRepository {
    Optional<Subscription> findById(@NotNull Long id);

    List<Subscription> save(@NotNull Long studentId, @NotBlank String courseName, @NotBlank String professor);

    void deleteById(@NotNull Long id);

    List<Subscription> findAll(@NotNull SortingAndOrderArguments args);

    List<Subscription> update(@NotNull Long id, String courseName);
}
