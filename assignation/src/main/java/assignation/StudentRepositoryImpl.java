package assignation;

import assignation.model.Student;
import io.micronaut.configuration.hibernate.jpa.scope.CurrentSession;
import io.micronaut.spring.tx.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class StudentRepositoryImpl implements StudentRepository {
    @PersistenceContext
    private EntityManager entityManager;  // <2>
    private final ApplicationConfiguration applicationConfiguration;

    public StudentRepositoryImpl(@CurrentSession EntityManager entityManager,
                                 ApplicationConfiguration applicationConfiguration) { // <2>
        this.entityManager = entityManager;
        this.applicationConfiguration = applicationConfiguration;
    }

    @Override
    @Transactional(readOnly = true) // <3>
    public Optional<Student> findById(@NotNull Long id) {
        return Optional.ofNullable(entityManager.find(Student.class, id));
    }

    @Override
    @Transactional // <4>
    public Student save(@NotBlank String name, String lastname, @NotBlank String email, @NotBlank String phone) {
        Student student = new Student(name, lastname, email, phone);
        entityManager.persist(student);
        return student;
    }

    @Override
    @Transactional
    public void deleteById(@NotNull Long id) {
        findById(id).ifPresent(genre -> entityManager.remove(genre));
    }

    private final static List<String> VALID_PROPERTY_NAMES = Arrays.asList("id", "name");

    @Transactional(readOnly = true)
    public List<Student> findAll(@NotNull SortingAndOrderArguments args) {
        String qlString = "SELECT g FROM Student as g";
        if (args.getOrder().isPresent() && args.getSort().isPresent() && VALID_PROPERTY_NAMES.contains(args.getSort().get())) {
            qlString += " ORDER BY g." + args.getSort().get() + " " + args.getOrder().get().toLowerCase();
        }
        TypedQuery<Student> query = entityManager.createQuery(qlString, Student.class);
        query.setMaxResults(args.getMax().orElseGet(applicationConfiguration::getMax));
        args.getOffset().ifPresent(query::setFirstResult);

        return query.getResultList();
    }

    @Override
    @Transactional
    public List<Student> update(@NotNull Long id, @NotBlank String name) {
        int fixed = entityManager.createQuery("UPDATE Student g SET firstName = :name where id = :id")
                .setParameter("name", name)
                .setParameter("id", id)
                .executeUpdate();

        String qlString = "SELECT g FROM Student as g WHERE id = :id";
        TypedQuery<Student> query = entityManager.createQuery(qlString, Student.class)
                .setParameter("id", id);

        return query.getResultList();
    }
}
