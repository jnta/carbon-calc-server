package br.com.actionlabs.carboncalc.servicesimpl;

import br.com.actionlabs.carboncalc.enums.TransportationType;
import br.com.actionlabs.carboncalc.model.TransportationEmissionFactor;
import br.com.actionlabs.carboncalc.repository.TransportationEmissionFactorRepository;
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
class TransportationCalculatorServiceImplTest {

    @Mock
    private TransportationEmissionFactorRepository transportationEmissionFactorRepository;

    @InjectMocks
    private TransportationCalculatorServiceImpl transportationCalculatorService;

    @Test
    void calculateTransportationEmission_validInput_returnsCorrectEmission() {
        TransportationType transportationType = TransportationType.CAR;
        BigDecimal distance = BigDecimal.valueOf(100);
        double factor = 0.2;

        when(transportationEmissionFactorRepository.findByType(transportationType))
                .thenReturn(Optional.of(new TransportationEmissionFactor(transportationType, factor)));

        BigDecimal result = transportationCalculatorService.calculateTransportationEmission(distance, transportationType);

        assertEquals(BigDecimal.valueOf(20.0), result);
    }

    @Test
    void calculateTransportationEmission_transportationTypeNotInDatabase_throwsException() {
        TransportationType transportationType = TransportationType.CAR;
        BigDecimal distance = BigDecimal.valueOf(100);

        when(transportationEmissionFactorRepository.findByType(transportationType))
                .thenReturn(Optional.empty());

        assertThrows(UnsupportedOperationException.class, () ->
                transportationCalculatorService.calculateTransportationEmission(distance, transportationType));
    }

    @Test
    void calculateTransportationEmission_zeroDistance_returnsZeroEmission() {
        TransportationType transportationType = TransportationType.CAR;
        BigDecimal distance = BigDecimal.ZERO;
        double factor = 0.2;

        when(transportationEmissionFactorRepository.findByType(transportationType))
                .thenReturn(Optional.of(new TransportationEmissionFactor(transportationType, factor)));

        BigDecimal result = transportationCalculatorService.calculateTransportationEmission(distance, transportationType);

        assertEquals(BigDecimal.ZERO.doubleValue(), result.doubleValue());
    }

    @Test
    void calculateTransportationEmission_negativeDistance_returnsNegativeEmission() {
        TransportationType transportationType = TransportationType.CAR;
        BigDecimal distance = BigDecimal.valueOf(-100);
        double factor = 0.2;

        when(transportationEmissionFactorRepository.findByType(transportationType))
                .thenReturn(Optional.of(new TransportationEmissionFactor(transportationType, factor)));

        BigDecimal result = transportationCalculatorService.calculateTransportationEmission(distance, transportationType);

        assertEquals(BigDecimal.valueOf(-20.0), result);
    }
}