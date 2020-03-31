package demo.ticket;

import demo.ticket.model.Order;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface OrderRepository {
    Optional<Order> findById(@NotNull Long id);

    Order save(@NotBlank String name, String lastname, @NotBlank String email, @NotBlank BigDecimal price);

    void deleteById(@NotNull Long id);

    List<Order> findAll(@NotNull SortingAndOrderArguments args);

    int update(@NotNull Long id, @NotBlank String name);
}
