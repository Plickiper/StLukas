package com.pecenio.appoms.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Mirrors the PatientEvent from the patidata module.
 * Used by the AppointmentSetter's Kafka consumer to deserialize
 * patient-events without a cross-service dependency.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PatientEvent {
    private String eventType;    // e.g., PatientRegisteredEvent
    private Integer patientId;
    private String message;
    private LocalDateTime timestamp;
}
