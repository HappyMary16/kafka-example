package org.example.repository;

import org.example.model.StatisticDb;
import org.springframework.data.repository.CrudRepository;

public interface StatisticRepository extends CrudRepository<StatisticDb, String>  {
}
