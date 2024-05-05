package org.example.api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cat {

    private String name;
    private CatBehaviour behaviour;
    private Instant firstDay = Instant.now();

    public Cat(String name, CatBehaviour behaviour) {
        this.name = name;
        this.behaviour = behaviour;
    }
}
