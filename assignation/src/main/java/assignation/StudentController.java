package assignation;

import assignation.model.Student;
import io.micronaut.http.HttpHeaders;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.*;
import io.micronaut.validation.Validated;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@Validated
@Controller("/students")
public class StudentController {

    protected final StudentRepository studentRepository ;

    @Inject
    private StudentClient orderClient;

    public StudentController(StudentRepository genreRepository) { // <3>
        this.studentRepository = genreRepository;
    }

    @Get("/{id}") // <4>
    public Student show(Long id) {
        return studentRepository
                .findById(id)
                .orElse(null); // <5>
    }

    @Put("/") // <6>
    public List<Student> update(@Body @Valid StudentUpdateCommand command) { // <7>
        List<Student> numberOfEntitiesUpdated = studentRepository.update(command.getId(), command.getName());
        for (Student student: numberOfEntitiesUpdated){
            orderClient.sendStudent(student.getId(), student.toJson());
        }
        return numberOfEntitiesUpdated;
    }

    @Get(value = "/list{?args*}") // <9>
    public List<Student> list(@Valid SortingAndOrderArguments args) {
        return studentRepository .findAll(args);
    }

    @Post("/") // <10>
    public HttpResponse<Student> save(@Body @Valid StudentSaveCommand cmd) {
        Student student = studentRepository .save(cmd.getFirstName(), cmd.getLastName(), cmd.getEmail(), cmd.getPhone());
        orderClient.sendStudent(student.getId(), student.toJson());
        return HttpResponse
                .created(student)
                .headers(headers -> headers.location(location(student.getId())));
    }

    @Delete("/{id}") // <11>
    public HttpResponse delete(Long id) {
        studentRepository .deleteById(id);
        return HttpResponse.noContent();
    }

    protected URI location(Long id) {
        return URI.create("/genres/" + id);
    }

    protected URI location(Student order) {
        return location(order.getId());
    }
}
