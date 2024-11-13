package br.com.actionlabs.carboncalc.servicesimpl;

import br.com.actionlabs.carboncalc.dto.TransportationDTO;
import br.com.actionlabs.carboncalc.exceptions.NotFoundException;
import br.com.actionlabs.carboncalc.model.CarbonCalculator;
import br.com.actionlabs.carboncalc.model.User;
import br.com.actionlabs.carboncalc.repository.CarbonCalculatorRepository;
import br.com.actionlabs.carboncalc.services.CarbonCalculatorService;
import br.com.actionlabs.carboncalc.services.EnergyCalculatorService;
import br.com.actionlabs.carboncalc.services.SolidWasteCalculatorService;
import br.com.actionlabs.carboncalc.services.TransportationCalculatorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

import static java.math.BigDecimal.valueOf;

@Service
@RequiredArgsConstructor
public class CarbonCalculatorServiceImpl implements CarbonCalculatorService {
    private final CarbonCalculatorRepository carbonCalculatorRepository;
    private final EnergyCalculatorService energyCalculatorService;
    private final TransportationCalculatorService transportationCalculatorService;
    private final SolidWasteCalculatorService solidWasteCalculatorService;

    @Override
    public String save(@Valid User user) {
        var carbonCalculator = CarbonCalculator.builder().user(user).build();
        return carbonCalculatorRepository.save(carbonCalculator).getId();
    }

    @Override
    public CarbonCalculator findById(String id) {
        return getCarbonCalculatorById(id);
    }

    @Override
    @Transactional
    public void calculateCarbonEmissionAndSave(String carbonCalculatorId, int energyConsumption, int solidWasteTotal, double recyclePercentage, List<TransportationDTO> transportationDTOList) {
        var carbonCalculator = getCarbonCalculatorById(carbonCalculatorId);
        var uf = carbonCalculator.getUser().getUf();

        BigDecimal energyEmissionTotal = calculateEnergyEmission(energyConsumption, uf);
        carbonCalculator.setEnergy(energyEmissionTotal.doubleValue());

        BigDecimal transportationTotal = calculateTotalTransportationEmission(transportationDTOList);
        carbonCalculator.setTransportation(transportationTotal.doubleValue());

        BigDecimal solidEmissionTotal = calculateSolidWasteEmission(solidWasteTotal, recyclePercentage, uf);
        carbonCalculator.setSolidWaste(solidEmissionTotal.doubleValue());

        carbonCalculator.setTotal(calculateTotalEmission(energyEmissionTotal, transportationTotal, solidEmissionTotal));
        carbonCalculatorRepository.save(carbonCalculator);
    }

    private CarbonCalculator getCarbonCalculatorById(String carbonCalculatorId) {
        return carbonCalculatorRepository.findById(carbonCalculatorId)
                .orElseThrow(() -> new NotFoundException(CarbonCalculator.class, carbonCalculatorId));
    }

    private BigDecimal calculateEnergyEmission(int energyConsumption, String uf) {
        return energyCalculatorService.calculateEnergyEmission(valueOf(energyConsumption), uf);
    }

    private BigDecimal calculateTotalTransportationEmission(List<TransportationDTO> transportationDTOList) {
        return transportationDTOList.stream()
                .map(this::calculateTransportationEmission)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private BigDecimal calculateTransportationEmission(TransportationDTO transportationDTO) {
        return transportationCalculatorService.calculateTransportationEmission(valueOf(transportationDTO.getMonthlyDistance()), transportationDTO.getType());
    }

    private BigDecimal calculateSolidWasteEmission(int solidWasteTotal, double recyclePercentage, String uf) {
        return solidWasteCalculatorService.calculateSolidEmission(valueOf(solidWasteTotal), valueOf(recyclePercentage), uf);
    }

    private double calculateTotalEmission(BigDecimal energyEmissionTotal, BigDecimal transportationTotal, BigDecimal solidEmissionTotal) {
        return energyEmissionTotal.add(transportationTotal).add(solidEmissionTotal).doubleValue();
    }
}
