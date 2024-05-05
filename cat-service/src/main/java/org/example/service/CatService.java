package org.example.service;

import org.example.api.event.EventAction;
import org.example.api.model.Cat;
import org.example.api.model.CatBehaviour;
import org.example.producer.CatEventProducer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CatService {

    private final Map<String, Cat> cats = new HashMap<>();

    private final String catsCafeName;
    private final CatEventProducer catEventProducer;

    public CatService(@Value("${cats-cafe.name}") String catsCafeName, CatEventProducer catEventProducer) {
        this.catsCafeName = catsCafeName;
        this.catEventProducer = catEventProducer;

        addCat(new Cat("Tom", CatBehaviour.CALM));
        addCat(new Cat("Oscar", CatBehaviour.NORMAL));
    }

    public Cat getCat(String catName) {
        return cats.get(catName);
    }

    public Collection<Cat> getAllCats() {
        return cats.values();
    }

    public Collection<Cat> getCatsByBehaviour(CatBehaviour catBehaviour) {
        List<Cat> filteredCats = new ArrayList<>();

        if (catBehaviour == null) {
            return filteredCats;
        }

        for (Cat cat : cats.values()) {
            if (catBehaviour.equals(cat.getBehaviour())) {
                filteredCats.add(cat);
            }
        }
        return filteredCats;
    }

    public boolean addCat(Cat cat) {
        if (cats.containsKey(cat.getName())) {
            return false;
        }

        cats.put(cat.getName(), cat);
        catEventProducer.sendCatEvent(catsCafeName, EventAction.CREATED, cat);

        return true;
    }

    public Cat updateCat(String catNumber, Cat cat) {
        cats.put(catNumber, cat);
        catEventProducer.sendCatEvent(catsCafeName, EventAction.UPDATED, cat);
        return cats.get(catNumber);
    }

    public Cat deleteCat(String catName) {
        catEventProducer.sendCatEvent(catsCafeName, EventAction.DELETED, cats.get(catName));
        return cats.remove(catName);
    }
}
