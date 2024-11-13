package br.com.actionlabs.carboncalc.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("energyEmissionFactor")
@NoArgsConstructor
@AllArgsConstructor
public class EnergyEmissionFactor {
    @Id
    private String uf;
    private double factor;
}
