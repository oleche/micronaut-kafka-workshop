package subscription;

import io.micronaut.configuration.kafka.annotation.KafkaClient;
import io.micronaut.configuration.kafka.annotation.KafkaKey;
import io.micronaut.configuration.kafka.annotation.Topic;

import javax.json.JsonObject;

@KafkaClient
public interface SubscriptionClient {
    @Topic(StudentStream.INPUT)
    void sendSubscription(@KafkaKey Long id, JsonObject order);
}

