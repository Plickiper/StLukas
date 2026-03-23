package com.pecenio.patims.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PatientEvent {
    private String eventType; // e.g., PatientRegisteredEvent
    private Integer patientId;
    private String message;
    private LocalDateTime timestamp;
}
