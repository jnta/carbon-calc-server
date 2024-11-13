package br.com.actionlabs.carboncalc.services;

import java.math.BigDecimal;

public interface SolidWasteCalculatorService {
    BigDecimal calculateSolidEmission(BigDecimal solidWaste, BigDecimal recyclePercentage, String uf);
}
