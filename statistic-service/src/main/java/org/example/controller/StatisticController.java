package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.api.model.Statistic;
import org.example.service.StatisticService;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RequiredArgsConstructor
@RequestMapping("/statistics")
@RestController
public class StatisticController {

    private final StatisticService statisticService;

    @GetMapping
    public Collection<Statistic> getAllStatistics() {
        return statisticService.getStatistics();
    }

    @GetMapping("/{catsCafeName}")
    public Statistic getCat(@PathVariable String catsCafeName) {
        return statisticService.getStatistic(catsCafeName);
    }
}
