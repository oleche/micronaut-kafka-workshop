package summary;

import io.micronaut.configuration.kafka.streams.ConfiguredStreamBuilder;
import io.micronaut.context.annotation.Factory;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.*;
import org.json.JSONException;
import org.json.JSONObject;

import javax.inject.Singleton;
import java.util.*;

@Factory
public class StudentStream {

    public static final String INPUT = "streams-student-input";
    public static final String STORE = "streams-student-store";
    public static final String OUTPUT = "streams-student-output";


    @Singleton
    KStream<String, String> studentStream(ConfiguredStreamBuilder builder) {
        // set default serdes
        Properties props = builder.getConfiguration();
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "172.17.0.3:9092");
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "summary-student");
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
        props.put(StreamsConfig.CACHE_MAX_BYTES_BUFFERING_CONFIG, 33554432);
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        KStream<String, String> source = builder.stream(INPUT);
        KTable<String, String> combinedStudent = source
                .map((k,v)-> new KeyValue<>(k,v))
                .groupByKey()
                .reduce(new StudentReducer());

        // need to override value serde to Long type
        combinedStudent.toStream().to(OUTPUT, Produced.with(Serdes.String(), Serdes.String()));

        return source;
    }

    public String getIdFromString(String value){
        try {
            JSONObject object = new JSONObject(value);
            if (object.has("id")){
                return object.get("id").toString().replace(" ", "");
            }else{
                return UUID.randomUUID().toString();
            }
        } catch (JSONException e) {
            return UUID.randomUUID().toString();
        }

    }

    public static class StudentReducer implements Reducer<String> {
        @Override
        public String apply(String value1, String value2) {
            try {
                System.out.print(value1);
                JSONObject object1 = new JSONObject(value1);
                JSONObject object2 = new JSONObject(value2);

                Iterator<String> keys1 = object1.keys();
                Iterator<String> keys2 = object2.keys();

                while(keys1.hasNext()) {
                    String key = keys1.next();
                    if (object2.has(key)){
                        object1.put(key, object2.get(key));
                    }
                }

                while(keys2.hasNext()) {
                    String key = keys2.next();
                    if (!object1.has(key)){
                        object1.put(key, object2.get(key));
                    }
                }

                System.out.println(object1);
                return object1.toString();

            } catch (JSONException e) {
                return "";
            }
        }

    }
}