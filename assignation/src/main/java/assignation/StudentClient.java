package assignation;

import io.micronaut.configuration.kafka.annotation.KafkaClient;
import io.micronaut.configuration.kafka.annotation.KafkaKey;
import io.micronaut.configuration.kafka.annotation.Topic;

import javax.json.JsonObject;

@KafkaClient
public interface StudentClient {
    @Topic(StudentStream.INPUT)
    void sendStudent(@KafkaKey Long id, JsonObject order);
}

