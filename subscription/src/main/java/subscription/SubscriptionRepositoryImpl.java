package subscription;

import io.micronaut.configuration.hibernate.jpa.scope.CurrentSession;
import io.micronaut.spring.tx.annotation.Transactional;
import subscription.model.Subscription;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class SubscriptionRepositoryImpl implements SubscriptionRepository {
    @PersistenceContext
    private EntityManager entityManager;  // <2>
    private final ApplicationConfiguration applicationConfiguration;

    public SubscriptionRepositoryImpl(@CurrentSession EntityManager entityManager,
                                      ApplicationConfiguration applicationConfiguration) { // <2>
        this.entityManager = entityManager;
        this.applicationConfiguration = applicationConfiguration;
    }

    @Override
    @Transactional(readOnly = true) // <3>
    public Optional<Subscription> findById(@NotNull Long id) {
        return Optional.ofNullable(entityManager.find(Subscription.class, id));
    }

    @Override
    @Transactional // <4>
    public List<Subscription> save(@NotNull Long studentId, @NotBlank String courseName, @NotBlank String professor) {
        Subscription subscription = new Subscription(studentId, courseName, professor);
        entityManager.persist(subscription);

        String qlString = "SELECT g FROM Subscription as g WHERE studentId = :id";
        TypedQuery<Subscription> query = entityManager.createQuery(qlString, Subscription.class)
                .setParameter("id", studentId);

        return query.getResultList();
    }

    @Override
    @Transactional
    public void deleteById(@NotNull Long id) {
        findById(id).ifPresent(genre -> entityManager.remove(genre));
    }

    private final static List<String> VALID_PROPERTY_NAMES = Arrays.asList("id", "name");

    @Transactional(readOnly = true)
    public List<Subscription> findAll(@NotNull SortingAndOrderArguments args) {
        String qlString = "SELECT g FROM Subscription as g";
        if (args.getOrder().isPresent() && args.getSort().isPresent() && VALID_PROPERTY_NAMES.contains(args.getSort().get())) {
            qlString += " ORDER BY g." + args.getSort().get() + " " + args.getOrder().get().toLowerCase();
        }
        TypedQuery<Subscription> query = entityManager.createQuery(qlString, Subscription.class);
        query.setMaxResults(args.getMax().orElseGet(applicationConfiguration::getMax));
        args.getOffset().ifPresent(query::setFirstResult);

        return query.getResultList();
    }

    @Override
    @Transactional
    public List<Subscription> update(@NotNull Long id, @NotBlank String courseName) {
        int fixed = entityManager.createQuery("UPDATE Subscription g SET courseName = :name where id = :id")
                .setParameter("name", courseName)
                .setParameter("id", id)
                .executeUpdate();

        String qlString = "SELECT g FROM Subscription as g WHERE id = :id";
        TypedQuery<Subscription> query = entityManager.createQuery(qlString, Subscription.class)
                .setParameter("id", id);

        return query.getResultList();
    }
}
