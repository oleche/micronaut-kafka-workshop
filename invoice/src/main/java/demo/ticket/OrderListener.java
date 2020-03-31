package demo.ticket;

import io.micronaut.configuration.kafka.annotation.KafkaKey;
import io.micronaut.configuration.kafka.annotation.KafkaListener;
import io.micronaut.configuration.kafka.annotation.OffsetReset;
import io.micronaut.configuration.kafka.annotation.Topic;

@KafkaListener(offsetReset = OffsetReset.EARLIEST)
public class OrderListener {
    @Topic("orders")
    public void receive(@KafkaKey String brand, String name) {
        System.out.println("Got Order - id " + name + " content " + brand);
    }
}
