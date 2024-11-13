package br.com.actionlabs.carboncalc.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class UpdateCalcInfoRequestDTO {
    @NotBlank
    private String id;
    private int energyConsumption;
    @Valid
    private List<TransportationDTO> transportation = new ArrayList<>();
    private int solidWasteTotal;

    @DecimalMin(value = "0.0", message = "must be between 0 and 1")
    @DecimalMax(value = "1.0", message = "must be between 0 and 1")
    private double recyclePercentage;
}
