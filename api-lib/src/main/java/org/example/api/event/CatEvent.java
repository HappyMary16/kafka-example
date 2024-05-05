package org.example.api.event;

import lombok.*;
import org.example.api.model.Cat;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class CatEvent extends AbstractKafkaEvent {

    private String catsCafeName;
    private Cat cat;

    @Builder
    public CatEvent(EventAction eventAction, String catsCafeName, Cat cat) {
        super(eventAction);
        this.catsCafeName = catsCafeName;
        this.cat = cat;
    }
}
