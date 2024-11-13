package br.com.actionlabs.carboncalc.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ErrorResponseDTO {
    private String message;
    private LocalDateTime timestamp;
    private String details;
    private boolean success;
}