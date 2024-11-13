package br.com.actionlabs.carboncalc.servicesimpl;

import br.com.actionlabs.carboncalc.exceptions.IllegalUfException;
import br.com.actionlabs.carboncalc.repository.EnergyEmissionFactorRepository;
import br.com.actionlabs.carboncalc.services.EnergyCalculatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class EnergyCalculatorServiceImpl implements EnergyCalculatorService {
    private final EnergyEmissionFactorRepository energyEmissionFactorRepository;

    @Override
    public BigDecimal calculateEnergyEmission(BigDecimal energyConsumption, String uf) {
        var energyEmissionFactor = energyEmissionFactorRepository.findByUf(uf)
                .orElseThrow(() -> new IllegalUfException(uf));
        return energyConsumption.multiply(BigDecimal.valueOf(energyEmissionFactor.getFactor()));
    }
}
