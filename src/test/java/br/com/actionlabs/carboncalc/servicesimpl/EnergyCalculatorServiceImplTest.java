package br.com.actionlabs.carboncalc.servicesimpl;

import br.com.actionlabs.carboncalc.exceptions.IllegalUfException;
import br.com.actionlabs.carboncalc.model.EnergyEmissionFactor;
import br.com.actionlabs.carboncalc.repository.EnergyEmissionFactorRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EnergyCalculatorServiceImplTest {

    @Mock
    private EnergyEmissionFactorRepository energyEmissionFactorRepository;

    @InjectMocks
    private EnergyCalculatorServiceImpl energyCalculatorService;

    @Test
    void calculateEnergyEmission_validInput_returnsCorrectEmission() {
        String uf = "SP";
        BigDecimal energyConsumption = BigDecimal.valueOf(100);
        double factor = 0.5;

        when(energyEmissionFactorRepository.findByUf(uf))
                .thenReturn(Optional.of(new EnergyEmissionFactor(uf, factor)));

        BigDecimal result = energyCalculatorService.calculateEnergyEmission(energyConsumption, uf);

        assertEquals(BigDecimal.valueOf(50.0).doubleValue(), result.doubleValue());
    }

    @Test
    void calculateEnergyEmission_invalidUf_throwsException() {
        String uf = "INVALID";
        BigDecimal energyConsumption = BigDecimal.valueOf(100);

        when(energyEmissionFactorRepository.findByUf(uf))
                .thenReturn(Optional.empty());

        assertThrows(IllegalUfException.class, () ->
                energyCalculatorService.calculateEnergyEmission(energyConsumption, uf));
    }

    @Test
    void calculateEnergyEmission_zeroEnergyConsumption_returnsZeroEmission() {
        String uf = "SP";
        BigDecimal energyConsumption = BigDecimal.ZERO;
        double factor = 0.5;

        when(energyEmissionFactorRepository.findByUf(uf))
                .thenReturn(Optional.of(new EnergyEmissionFactor(uf, factor)));

        BigDecimal result = energyCalculatorService.calculateEnergyEmission(energyConsumption, uf);

        assertEquals(BigDecimal.ZERO.doubleValue(), result.doubleValue());
    }

    @Test
    void calculateEnergyEmission_negativeEnergyConsumption_returnsNegativeEmission() {
        String uf = "SP";
        BigDecimal energyConsumption = BigDecimal.valueOf(-100);
        double factor = 0.5;

        when(energyEmissionFactorRepository.findByUf(uf))
                .thenReturn(Optional.of(new EnergyEmissionFactor(uf, factor)));

        BigDecimal result = energyCalculatorService.calculateEnergyEmission(energyConsumption, uf);

        assertEquals(BigDecimal.valueOf(-50.0).doubleValue(), result.doubleValue());
    }
}