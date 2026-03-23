package com.pecenio.appoms.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentEvent {
    private String eventType; // e.g., AppointmentRequestedEvent, AppointmentAssignedEvent
    private Integer appointmentId;
    private Long patientId;
    private Long doctorId;
    private String status;
    private String message;
    private LocalDateTime timestamp;
}
