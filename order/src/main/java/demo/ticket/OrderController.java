package demo.ticket;

import demo.ticket.model.Order;
import io.micronaut.http.HttpHeaders;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.*;
import io.micronaut.validation.Validated;

import javax.inject.Inject;
import javax.json.JsonObject;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.net.URI;
import java.util.List;

@Validated
@Controller("/orders")
public class OrderController {

    protected final OrderRepository genreRepository;

    @Inject
    private OrderClient orderClient;

    public OrderController(OrderRepository genreRepository) { // <3>
        this.genreRepository = genreRepository;
    }

    @Get("/{id}") // <4>
    public Order show(Long id) {
        return genreRepository
                .findById(id)
                .orElse(null); // <5>
    }

    @Put("/") // <6>
    public HttpResponse update(@Body @Valid OrderUpdateCommand command) { // <7>
        int numberOfEntitiesUpdated = genreRepository.update(command.getId(), command.getName());

        return HttpResponse
                .noContent()
                .header(HttpHeaders.LOCATION, location(command.getId()).getPath()); // <8>
    }

    @Get(value = "/list{?args*}") // <9>
    public List<Order> list(@Valid SortingAndOrderArguments args) {
        return genreRepository.findAll(args);
    }

    @Post("/") // <10>
    public HttpResponse<Order> save(@Body @Valid OrderSaveCommand cmd) {
        Order order = genreRepository.save(cmd.getFirstName(), cmd.getLastName(), cmd.getEmail(), new BigDecimal(cmd.getPrice()));
        orderClient.sendProduct(order.getId(), order.toJson());
        return HttpResponse
                .created(order)
                .headers(headers -> headers.location(location(order.getId())));
    }

    @Delete("/{id}") // <11>
    public HttpResponse delete(Long id) {
        genreRepository.deleteById(id);
        return HttpResponse.noContent();
    }

    protected URI location(Long id) {
        return URI.create("/genres/" + id);
    }

    protected URI location(Order order) {
        return location(order.getId());
    }
}
