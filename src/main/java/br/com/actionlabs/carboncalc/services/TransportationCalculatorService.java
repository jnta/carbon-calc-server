package br.com.actionlabs.carboncalc.services;

import br.com.actionlabs.carboncalc.enums.TransportationType;

import java.math.BigDecimal;

public interface TransportationCalculatorService {
    BigDecimal calculateTransportationEmission(BigDecimal distance, TransportationType transportationType);
}
