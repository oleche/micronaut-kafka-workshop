package summary;

import io.micronaut.configuration.kafka.annotation.KafkaKey;
import io.micronaut.configuration.kafka.annotation.KafkaListener;
import io.micronaut.configuration.kafka.annotation.OffsetReset;
import io.micronaut.configuration.kafka.annotation.Topic;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.io.StringReader;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@KafkaListener(groupId="summary-student", offsetReset = OffsetReset.EARLIEST)
public class StudentListener {

    private final Map<Long, JsonObject> students = new ConcurrentHashMap<>();

    @Topic(StudentStream.OUTPUT)
    public void receive(@KafkaKey Long id, String order) {
        System.out.println("Got Order - id " + id + " content " + order);
        JsonReader jsonReader = Json.createReader(new StringReader(order));
        JsonObject object = jsonReader.readObject();
        jsonReader.close();
        students.put(id, object);
    }

    public JsonObject getCount(Long id) {
        JsonObject student = students.get(id);
        if (student != null) {
            return student;
        }
        return null;
    }

    public Map<Long, JsonObject> getStudents() {
        return Collections.unmodifiableMap(students);
    }
}
