package br.com.actionlabs.carboncalc.rest;

import br.com.actionlabs.carboncalc.dto.*;
import br.com.actionlabs.carboncalc.mapper.UserMapper;
import br.com.actionlabs.carboncalc.services.CarbonCalculatorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/open")
@RequiredArgsConstructor
@Slf4j
@Validated
public class OpenRestController {
    private final CarbonCalculatorService carbonCalculatorService;

    @PostMapping("start-calc")
    public ResponseEntity<StartCalcResponseDTO> startCalculation(
            @RequestBody @Valid StartCalcRequestDTO request) {
        String id = carbonCalculatorService.save(UserMapper.mapToUser(request));
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(StartCalcResponseDTO.builder().id(id).build());
    }

    @PutMapping("info")
    public ResponseEntity<UpdateCalcInfoResponseDTO> updateInfo(
            @RequestBody @Valid UpdateCalcInfoRequestDTO request) {
        carbonCalculatorService.calculateCarbonEmissionAndSave(request.getId(), request.getEnergyConsumption(),
                request.getSolidWasteTotal(), request.getRecyclePercentage(), request.getTransportation());
        return ResponseEntity.ok(UpdateCalcInfoResponseDTO.builder().success(true).build());
    }

    @GetMapping("result/{id}")
    public ResponseEntity<CarbonCalculationResultDTO> getResult(@PathVariable String id) {
        var carbonCalculator = carbonCalculatorService.findById(id);
        return ResponseEntity.ok(CarbonCalculationResultDTO.builder()
                .energy(carbonCalculator.getEnergy())
                .solidWaste(carbonCalculator.getSolidWaste())
                .transportation(carbonCalculator.getTransportation())
                .total(carbonCalculator.getTotal())
                .build());
    }
}
