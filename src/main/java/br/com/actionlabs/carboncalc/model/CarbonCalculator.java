package br.com.actionlabs.carboncalc.model;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("carbonCalculator")
@Builder
public class CarbonCalculator {
    @Id
    private String id;
    @NotNull
    private User user;
    private Double energy;
    private Double transportation;
    private Double solidWaste;
    private Double total;
}
