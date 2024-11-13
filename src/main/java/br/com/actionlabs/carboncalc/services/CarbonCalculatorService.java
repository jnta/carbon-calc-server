package br.com.actionlabs.carboncalc.services;

import br.com.actionlabs.carboncalc.dto.TransportationDTO;
import br.com.actionlabs.carboncalc.model.CarbonCalculator;
import br.com.actionlabs.carboncalc.model.User;

import java.util.List;

public interface CarbonCalculatorService {
    String save(User user);

    CarbonCalculator findById(String id);

    void calculateCarbonEmissionAndSave(String carbonCalculatorId, int energyConsumption, int solidWasteTotal, double recyclePercentage, List<TransportationDTO> transportationDTOList);
}
