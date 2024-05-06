package org.example.producer;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.example.api.event.CatEvent;
import org.example.api.event.EventAction;
import org.example.api.model.Cat;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

/**
 * Sends {@link CatEvent}s to topic
 * specified in property "cats-cafe.kafka.topic.cats"
 */
@Component
public class CatEventProducer {

    private final KafkaTemplate<String, CatEvent> catKafkaTemplate;

    public CatEventProducer(KafkaTemplate<String, CatEvent> catKafkaTemplate) {
        this.catKafkaTemplate = catKafkaTemplate;
    }

    @Value("${cats-cafe.kafka.topic.cats}")
    private String catsTopic;

    public void sendCatEvent(String catsCafeName, EventAction action, Cat cat) {
        CatEvent catEvent = CatEvent.builder()
                                    .catsCafeName(catsCafeName)
                                    .eventAction(action)
                                    .cat(cat)
                                    .build();

        catKafkaTemplate.send(new ProducerRecord<>(catsTopic, catEvent));
        System.out.println("Sent message [" + catEvent + "] to " + catsTopic);
    }
}
