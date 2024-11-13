package br.com.actionlabs.carboncalc.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("solidWasteEmissionFactor")
@AllArgsConstructor
@NoArgsConstructor
public class SolidWasteEmissionFactor {
    @Id
    private String uf;
    private double recyclableFactor;
    private double nonRecyclableFactor;
}
