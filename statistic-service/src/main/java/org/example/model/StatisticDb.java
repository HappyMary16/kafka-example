package org.example.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "statistics")
@Entity
public class StatisticDb {

    @Id
    private String catsCafeName;
    private int currentCatsCount;
}
