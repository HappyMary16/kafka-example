package org.example.consumer;

import lombok.RequiredArgsConstructor;
import org.example.api.event.CatEvent;
import org.example.api.event.EventAction;
import org.example.service.StatisticService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * Consumes all events that are sent to topic
 * specified in property "cats-cafe.kafka.topic.cats"
 */
@RequiredArgsConstructor
@Component
public class CatEventsConsumer {

    private final StatisticService statisticService;

    @KafkaListener(topics = "${cats-cafe.kafka.topic.cats}")
    public void carEventListener(CatEvent carEvent) {
        System.out.println("Received car event: " + carEvent);

        if (carEvent.getEventAction() == EventAction.CREATED) {
            statisticService.increaseCatsCount(carEvent.getCatsCafeName());
        }

        if (carEvent.getEventAction() == EventAction.DELETED) {
            statisticService.decreaseCatsCount(carEvent.getCatsCafeName());
        }
    }
}
