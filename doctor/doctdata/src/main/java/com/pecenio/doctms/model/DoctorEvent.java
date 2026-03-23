package com.pecenio.doctms.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DoctorEvent {
    private String eventType; // e.g., DoctorAvailabilityChangedEvent
    private Integer doctorId;
    private String availabilityStatus;
    private String message;
    private LocalDateTime timestamp;
}
