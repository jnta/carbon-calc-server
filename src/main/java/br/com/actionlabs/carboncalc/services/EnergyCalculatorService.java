package br.com.actionlabs.carboncalc.services;

import java.math.BigDecimal;

public interface EnergyCalculatorService {
    BigDecimal calculateEnergyEmission(BigDecimal energyConsumption, String uf);
}
