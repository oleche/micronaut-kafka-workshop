package demo.ticket;

import demo.ticket.model.Order;
import io.micronaut.configuration.hibernate.jpa.scope.CurrentSession;
import io.micronaut.spring.tx.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class OrderRepositoryImpl implements OrderRepository {
    @PersistenceContext
    private EntityManager entityManager;  // <2>
    private final ApplicationConfiguration applicationConfiguration;

    public OrderRepositoryImpl(@CurrentSession EntityManager entityManager,
                               ApplicationConfiguration applicationConfiguration) { // <2>
        this.entityManager = entityManager;
        this.applicationConfiguration = applicationConfiguration;
    }

    @Override
    @Transactional(readOnly = true) // <3>
    public Optional<Order> findById(@NotNull Long id) {
        return Optional.ofNullable(entityManager.find(Order.class, id));
    }

    @Override
    @Transactional // <4>
    public Order save(@NotBlank String name, String lastname, @NotBlank String email, @NotBlank BigDecimal price) {
        Order genre = new Order(name, lastname, email, price);
        entityManager.persist(genre);
        return genre;
    }

    @Override
    @Transactional
    public void deleteById(@NotNull Long id) {
        findById(id).ifPresent(genre -> entityManager.remove(genre));
    }

    private final static List<String> VALID_PROPERTY_NAMES = Arrays.asList("id", "name");

    @Transactional(readOnly = true)
    public List<Order> findAll(@NotNull SortingAndOrderArguments args) {
        String qlString = "SELECT g FROM Order as g";
        if (args.getOrder().isPresent() && args.getSort().isPresent() && VALID_PROPERTY_NAMES.contains(args.getSort().get())) {
            qlString += " ORDER BY g." + args.getSort().get() + " " + args.getOrder().get().toLowerCase();
        }
        TypedQuery<Order> query = entityManager.createQuery(qlString, Order.class);
        query.setMaxResults(args.getMax().orElseGet(applicationConfiguration::getMax));
        args.getOffset().ifPresent(query::setFirstResult);

        return query.getResultList();
    }

    @Override
    @Transactional
    public int update(@NotNull Long id, @NotBlank String name) {
        return entityManager.createQuery("UPDATE Order g SET name = :name where id = :id")
                .setParameter("name", name)
                .setParameter("id", id)
                .executeUpdate();
    }
}
