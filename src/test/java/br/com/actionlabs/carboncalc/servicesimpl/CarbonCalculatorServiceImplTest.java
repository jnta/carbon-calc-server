package br.com.actionlabs.carboncalc.servicesimpl;

import br.com.actionlabs.carboncalc.dto.TransportationDTO;
import br.com.actionlabs.carboncalc.enums.TransportationType;
import br.com.actionlabs.carboncalc.exceptions.NotFoundException;
import br.com.actionlabs.carboncalc.model.CarbonCalculator;
import br.com.actionlabs.carboncalc.model.User;
import br.com.actionlabs.carboncalc.repository.CarbonCalculatorRepository;
import br.com.actionlabs.carboncalc.services.EnergyCalculatorService;
import br.com.actionlabs.carboncalc.services.SolidWasteCalculatorService;
import br.com.actionlabs.carboncalc.services.TransportationCalculatorService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CarbonCalculatorServiceImplTest {
    private static final User USER = new User("John", "john@email.com", "SP", "123");

    @Mock
    private CarbonCalculatorRepository carbonCalculatorRepository;

    @Mock
    private EnergyCalculatorService energyCalculatorService;

    @Mock
    private TransportationCalculatorService transportationCalculatorService;

    @Mock
    private SolidWasteCalculatorService solidWasteCalculatorService;

    @InjectMocks
    private CarbonCalculatorServiceImpl carbonCalculatorService;

    @Test
    void save_validUser_returnsCarbonCalculatorId() {
        CarbonCalculator carbonCalculator = CarbonCalculator.builder().user(USER).build();
        when(carbonCalculatorRepository.save(carbonCalculator)).thenReturn(carbonCalculator);

        String result = carbonCalculatorService.save(USER);

        assertEquals(carbonCalculator.getId(), result);
    }

    @Test
    void findById_existingId_returnsCarbonCalculator() {
        String id = "123";
        CarbonCalculator carbonCalculator = new CarbonCalculator();
        when(carbonCalculatorRepository.findById(id)).thenReturn(Optional.of(carbonCalculator));

        CarbonCalculator result = carbonCalculatorService.findById(id);

        assertEquals(carbonCalculator, result);
    }

    @Test
    void findById_nonExistingId_throwsNotFoundException() {
        String id = "123";
        when(carbonCalculatorRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> carbonCalculatorService.findById(id));
    }

    @Test
    void calculateCarbonEmissionAndSave_validInput_savesCarbonCalculator() {
        String carbonCalculatorId = "123";
        int energyConsumption = 100;
        int solidWasteTotal = 50;
        double recyclePercentage = 0.3;
        List<TransportationDTO> transportationDTOList = List.of(new TransportationDTO(TransportationType.CAR, 100));

        CarbonCalculator carbonCalculator = CarbonCalculator.builder().user(USER).build();
        when(carbonCalculatorRepository.findById(carbonCalculatorId)).thenReturn(Optional.of(carbonCalculator));
        when(energyCalculatorService.calculateEnergyEmission(BigDecimal.valueOf(energyConsumption), "SP")).thenReturn(BigDecimal.valueOf(50));
        when(transportationCalculatorService.calculateTransportationEmission(any(BigDecimal.class), any(TransportationType.class))).thenReturn(BigDecimal.valueOf(20));
        when(solidWasteCalculatorService.calculateSolidEmission(BigDecimal.valueOf(solidWasteTotal), BigDecimal.valueOf(recyclePercentage), "SP")).thenReturn(BigDecimal.valueOf(10));

        carbonCalculatorService.calculateCarbonEmissionAndSave(carbonCalculatorId, energyConsumption, solidWasteTotal, recyclePercentage, transportationDTOList);

        assertEquals(50.0, carbonCalculator.getEnergy());
        assertEquals(20.0, carbonCalculator.getTransportation());
        assertEquals(10.0, carbonCalculator.getSolidWaste());
        assertEquals(80.0, carbonCalculator.getTotal());
    }

    @Test
    void calculateCarbonEmissionAndSave_nonExistingCarbonCalculatorId_throwsNotFoundException() {
        String carbonCalculatorId = "123";
        when(carbonCalculatorRepository.findById(carbonCalculatorId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> carbonCalculatorService.calculateCarbonEmissionAndSave(carbonCalculatorId, 100, 50, 0.3, List.of(new TransportationDTO())));
    }
}