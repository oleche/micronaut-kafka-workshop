package subscription.dto;

import subscription.model.Subscription;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import java.util.List;

public class StudentSubscription {

    public StudentSubscription(List<Subscription> subscriptions) {
        this.subscriptions = subscriptions;
    }

    List<Subscription> subscriptions;

    public List<Subscription> getSubscriptions() {
        return subscriptions;
    }

    public void setSubscriptions(List<Subscription> subscriptions) {
        this.subscriptions = subscriptions;
    }

    public JsonObject toJson() {
        JsonArrayBuilder builder = Json.createArrayBuilder();
        for (Subscription subscription: subscriptions){
            builder.add(subscription.toJson());
        }

        return Json.createObjectBuilder()
                .add("subscriptions", builder.build())
                .build();
    }
}
