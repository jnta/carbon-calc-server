package br.com.actionlabs.carboncalc.servicesimpl;

import br.com.actionlabs.carboncalc.model.SolidWasteEmissionFactor;
import br.com.actionlabs.carboncalc.repository.SolidWasteEmissionFactorRepository;
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
class SolidWasteCalculatorServiceImplTest {

    @Mock
    private SolidWasteEmissionFactorRepository solidWasteEmissionFactorRepository;

    @InjectMocks
    private SolidWasteCalculatorServiceImpl solidWasteCalculatorService;

    @Test
    void calculateSolidEmission_validInput_returnsCorrectEmission() {
        String uf = "SP";
        BigDecimal solidWasteTotal = BigDecimal.valueOf(100);
        BigDecimal recyclePercentage = BigDecimal.valueOf(0.3);
        double recyclableFactor = 0.1;
        double nonRecyclableFactor = 0.2;

        when(solidWasteEmissionFactorRepository.findByUf(uf))
                .thenReturn(Optional.of(new SolidWasteEmissionFactor(uf, recyclableFactor, nonRecyclableFactor)));

        BigDecimal result = solidWasteCalculatorService.calculateSolidEmission(solidWasteTotal, recyclePercentage, uf);

        assertEquals(BigDecimal.valueOf(17.0).doubleValue(), result.doubleValue());
    }

    @Test
    void calculateSolidEmission_invalidUf_throwsException() {
        String uf = "INVALID";
        BigDecimal solidWasteTotal = BigDecimal.valueOf(100);
        BigDecimal recyclePercentage = BigDecimal.valueOf(0.3);

        when(solidWasteEmissionFactorRepository.findByUf(uf))
                .thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () ->
                solidWasteCalculatorService.calculateSolidEmission(solidWasteTotal, recyclePercentage, uf));
    }

    @Test
    void calculateSolidEmission_zeroSolidWasteTotal_returnsZeroEmission() {
        String uf = "SP";
        BigDecimal solidWasteTotal = BigDecimal.ZERO;
        BigDecimal recyclePercentage = BigDecimal.valueOf(0.3);
        double recyclableFactor = 0.1;
        double nonRecyclableFactor = 0.2;

        when(solidWasteEmissionFactorRepository.findByUf(uf))
                .thenReturn(Optional.of(new SolidWasteEmissionFactor(uf, recyclableFactor, nonRecyclableFactor)));

        BigDecimal result = solidWasteCalculatorService.calculateSolidEmission(solidWasteTotal, recyclePercentage, uf);

        assertEquals(BigDecimal.ZERO.doubleValue(), result.doubleValue());
    }

    @Test
    void calculateSolidEmission_negativeSolidWasteTotal_returnsNegativeEmission() {
        String uf = "SP";
        BigDecimal solidWasteTotal = BigDecimal.valueOf(-100);
        BigDecimal recyclePercentage = BigDecimal.valueOf(0.3);
        double recyclableFactor = 0.1;
        double nonRecyclableFactor = 0.2;

        when(solidWasteEmissionFactorRepository.findByUf(uf))
                .thenReturn(Optional.of(new SolidWasteEmissionFactor(uf, recyclableFactor, nonRecyclableFactor)));

        BigDecimal result = solidWasteCalculatorService.calculateSolidEmission(solidWasteTotal, recyclePercentage, uf);

        assertEquals(BigDecimal.valueOf(-17.0).doubleValue(), result.doubleValue());
    }

    @Test
    void calculateSolidEmission_recyclePercentageGreaterThanOne_returnsCorrectEmission() {
        String uf = "SP";
        BigDecimal solidWasteTotal = BigDecimal.valueOf(100);
        BigDecimal recyclePercentage = BigDecimal.valueOf(1.5);
        double recyclableFactor = 0.1;
        double nonRecyclableFactor = 0.2;

        when(solidWasteEmissionFactorRepository.findByUf(uf))
                .thenReturn(Optional.of(new SolidWasteEmissionFactor(uf, recyclableFactor, nonRecyclableFactor)));

        BigDecimal result = solidWasteCalculatorService.calculateSolidEmission(solidWasteTotal, recyclePercentage, uf);

        assertEquals(BigDecimal.valueOf(5.0).doubleValue(), result.doubleValue());
    }
}