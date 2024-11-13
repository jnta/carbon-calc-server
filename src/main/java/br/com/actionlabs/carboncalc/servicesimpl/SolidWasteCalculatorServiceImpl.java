package br.com.actionlabs.carboncalc.servicesimpl;

import br.com.actionlabs.carboncalc.repository.SolidWasteEmissionFactorRepository;
import br.com.actionlabs.carboncalc.services.SolidWasteCalculatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

import static java.math.BigDecimal.valueOf;

@Service
@RequiredArgsConstructor
public class SolidWasteCalculatorServiceImpl implements SolidWasteCalculatorService {
    private final SolidWasteEmissionFactorRepository solidWasteEmissionFactorRepository;

    @Override
    public BigDecimal calculateSolidEmission(BigDecimal solidWasteTotal, BigDecimal recyclePercentage, String uf) {
        var factors = solidWasteEmissionFactorRepository.findByUf(uf)
                .orElseThrow(() -> new IllegalArgumentException("Invalid UF value: " + uf));

        var recyclableEmission = solidWasteTotal.multiply(recyclePercentage)
                .multiply(valueOf(factors.getRecyclableFactor()));
        var nonRecyclableEmission = solidWasteTotal.multiply(BigDecimal.ONE.subtract(recyclePercentage))
                .multiply(valueOf(factors.getNonRecyclableFactor()));
        return recyclableEmission.add(nonRecyclableEmission);
    }
}
