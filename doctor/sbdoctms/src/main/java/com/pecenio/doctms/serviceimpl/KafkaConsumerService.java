package com.pecenio.doctms.serviceimpl;

import com.pecenio.doctms.model.AppointmentEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

/**
 * Kafka Consumer for the Doctor Service.
 *
 * Listens to the "appointment-events" topic.
 * When an appointment is CONFIRMED (assigned to this doctor), the doctor
 * is notified and can then view the appointment via GET /api/doctor/{id}.
 *
 * This ensures the doctor service stays operational even if the
 * AppointmentSetter service is temporarily down.
 */
@Service
public class KafkaConsumerService {

    private static final Logger logger = LoggerFactory.getLogger(KafkaConsumerService.class);

    /**
     * Consumes appointment lifecycle events.
     * EventTypes expected:
     *  - "AppointmentRequestedEvent"  → new request submitted by patient
     *  - "AppointmentAssignedEvent"   → admin assigned this doctor
     *  - "AppointmentCancelledEvent"  → appointment was cancelled
     *  - "AppointmentCompletedEvent"  → appointment marked complete
     */
    @KafkaListener(
        topics = "appointment-events",
        groupId = "doctor-service-group"
    )
    public void consumeAppointmentEvent(AppointmentEvent event) {
        logger.info("[Doctor Service] Received appointment event: type={}, appointmentId={}, doctorId={}, status={}",
            event.getEventType(),
            event.getAppointmentId(),
            event.getDoctorId(),
            event.getStatus()
        );

        switch (event.getEventType()) {
            case "AppointmentAssignedEvent":
                logger.info("[Doctor Service] Doctor {} has been assigned appointment {}. Status: {}",
                    event.getDoctorId(), event.getAppointmentId(), event.getStatus());
                break;

            case "AppointmentCancelledEvent":
                logger.info("[Doctor Service] Appointment {} has been CANCELLED.", event.getAppointmentId());
                break;

            case "AppointmentCompletedEvent":
                logger.info("[Doctor Service] Appointment {} marked COMPLETED.", event.getAppointmentId());
                break;

            default:
                logger.debug("[Doctor Service] Ignoring event type: {}", event.getEventType());
        }
    }
}
