package summary;

import io.micronaut.http.annotation.*;
import io.micronaut.validation.Validated;

import javax.inject.Inject;
import javax.json.JsonObject;
import java.util.ArrayList;
import java.util.List;

@Validated
@Controller("/students")
public class StudentController {

    @Inject
    private StudentListener orderListener;

    @Get("/{id}") // <4>
    public JsonObject show(Long id) {
        return orderListener.getCount(id);
    }

    @Get(value = "/") // <9>
    public List<JsonObject> list() {
        return new ArrayList<>( orderListener.getStudents().values() );
    }
}
