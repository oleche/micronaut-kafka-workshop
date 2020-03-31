package subscription;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.*;
import io.micronaut.validation.Validated;
import subscription.dto.StudentSubscription;
import subscription.model.Subscription;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@Validated
@Controller("/subscriptions")
public class SubscriptionController {

    protected final SubscriptionRepository subscriptionRepository ;

    @Inject
    private SubscriptionClient subscriptionClient;

    public SubscriptionController(SubscriptionRepository genreRepository) { // <3>
        this.subscriptionRepository = genreRepository;
    }

    @Get("/{id}") // <4>
    public Subscription show(Long id) {
        return subscriptionRepository
                .findById(id)
                .orElse(null); // <5>
    }

    @Put("/") // <6>
    public List<Subscription> update(@Body @Valid SubscriptionUpdateCommand command) { // <7>
        List<Subscription> numberOfEntitiesUpdated = subscriptionRepository.update(command.getId(), command.getCourseName());
        for (Subscription student: numberOfEntitiesUpdated){
            subscriptionClient.sendSubscription(student.getId(), student.toJson());
        }
        return numberOfEntitiesUpdated;
    }

    @Get(value = "/list{?args*}") // <9>
    public List<Subscription> list(@Valid SortingAndOrderArguments args) {
        return subscriptionRepository .findAll(args);
    }

    @Post("/") // <10>
    public List<Subscription> save(@Body @Valid SubscriptionSaveCommand cmd) {
        List<Subscription> students = subscriptionRepository .save(cmd.getStudentId(), cmd.getCourseName(), cmd.getProfessor());

        StudentSubscription studentSubscription = new StudentSubscription(students);
        subscriptionClient.sendSubscription(cmd.getStudentId(), studentSubscription.toJson());
        return students;
    }

    @Delete("/{id}") // <11>
    public HttpResponse delete(Long id) {
        subscriptionRepository .deleteById(id);
        return HttpResponse.noContent();
    }

    protected URI location(Long id) {
        return URI.create("/genres/" + id);
    }

    protected URI location(Subscription order) {
        return location(order.getId());
    }
}
