package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.api.model.Statistic;
import org.example.model.StatisticDb;
import org.example.repository.StatisticRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RequiredArgsConstructor
@Service
public class StatisticService {

    private final StatisticRepository statisticRepository;

    public List<Statistic> getStatistics() {
        return StreamSupport.stream(statisticRepository.findAll().spliterator(), false)
                            .map(statistic -> Statistic.builder()
                                                       .catsCafeName(statistic.getCatsCafeName())
                                                       .currentCatsCount(statistic.getCurrentCatsCount())
                                                       .build())
                            .collect(Collectors.toList());
    }

    public Statistic getStatistic(String catsCafeName) {
        return statisticRepository.findById(catsCafeName)
                                  .map(statistic -> Statistic.builder()
                                                             .catsCafeName(catsCafeName)
                                                             .currentCatsCount(statistic.getCurrentCatsCount())
                                                             .build())
                                  .orElseThrow(() -> new ResponseStatusException(NOT_FOUND));
    }

    /**
     * Increases current number of cats in cafe.
     *
     * @param catsCafeName - to that current number of cars will be increased
     */
    public void increaseCatsCount(String catsCafeName) {
        StatisticDb statistic = getStatisticForParking(catsCafeName);
        StatisticDb updatedStatistic = statistic.toBuilder()
                                                .currentCatsCount(statistic.getCurrentCatsCount() + 1)
                                                .build();

        statisticRepository.save(updatedStatistic);
    }

    /**
     * Decreases current number of cats in cafe.
     *
     * @param catsCafeName - to that current number of cars will be decreased
     */
    public void decreaseCatsCount(String catsCafeName) {
        StatisticDb statistic = getStatisticForParking(catsCafeName);
        StatisticDb updatedStatistic = statistic.toBuilder()
                                                .currentCatsCount(statistic.getCurrentCatsCount() - 1)
                                                .build();

        statisticRepository.save(updatedStatistic);
    }

    private StatisticDb getStatisticForParking(String parkingName) {
        if (!statisticRepository.existsById(parkingName)) {
            StatisticDb emptyStatistic = StatisticDb.builder()
                                                    .catsCafeName(parkingName)
                                                    .currentCatsCount(0)
                                                    .build();

            return statisticRepository.save(emptyStatistic);
        }

        return statisticRepository.findById(parkingName)
                                  .orElseThrow();
    }
}
