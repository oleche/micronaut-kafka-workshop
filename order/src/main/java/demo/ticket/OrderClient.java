package demo.ticket;

import io.micronaut.configuration.kafka.annotation.KafkaClient;
import io.micronaut.configuration.kafka.annotation.KafkaKey;
import io.micronaut.configuration.kafka.annotation.Topic;

import javax.json.JsonObject;

@KafkaClient
public interface OrderClient {
    @Topic("orders")
    void sendProduct(@KafkaKey Long id, JsonObject order);
}
