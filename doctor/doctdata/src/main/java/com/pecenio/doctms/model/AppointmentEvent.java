package com.pecenio.doctms.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Mirrors the AppointmentEvent from the appoms module.
 * Needed so the doctor-service Kafka consumer can deserialize messages
 * without depending directly on the appodata artifact.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentEvent {
    private String eventType;       // e.g., AppointmentAssignedEvent
    private Integer appointmentId;
    private Long patientId;
    private Long doctorId;
    private String status;          // REQUESTED / CONFIRMED / REJECTED / COMPLETED
    private String message;
    private LocalDateTime timestamp;
}
