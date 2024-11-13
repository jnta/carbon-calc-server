package br.com.actionlabs.carboncalc.servicesimpl;

import br.com.actionlabs.carboncalc.enums.TransportationType;
import br.com.actionlabs.carboncalc.repository.TransportationEmissionFactorRepository;
import br.com.actionlabs.carboncalc.services.TransportationCalculatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class TransportationCalculatorServiceImpl implements TransportationCalculatorService {
    private final TransportationEmissionFactorRepository transportationEmissionFactorRepository;

    @Override
    public BigDecimal calculateTransportationEmission(BigDecimal distance, TransportationType transportationType) {
        var transportationEmissionFactor = transportationEmissionFactorRepository.findByType(transportationType)
                .orElseThrow(() -> new UnsupportedOperationException(String.format("Transportation type %s not present in database", transportationType)));
        return distance.multiply(BigDecimal.valueOf(transportationEmissionFactor.getFactor()));
    }
}
