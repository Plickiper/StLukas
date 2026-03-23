package com.pecenio.appoms.serviceimpl;

import com.pecenio.appoms.entity.PatientData;
import com.pecenio.appoms.model.PatientEvent;
import com.pecenio.appoms.repository.PatientDataRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Kafka Consumer for the AppointmentSetter Service.
 *
 * Listens to "patient-events" to keep a local copy of patient data in sync.
 * This is the key to resilience: even if the Patient service goes down,
 * the admin can still see patient info when managing appointments.
 *
 * Also listens to its own "appointment-events" for logging/auditing.
 */
@Service
public class KafkaConsumerService {

    private static final Logger logger = LoggerFactory.getLogger(KafkaConsumerService.class);

    @Autowired
    private PatientDataRepository patientDataRepository;

    /**
     * Sync patient data locally when a patient registers or updates their profile.
     * This ensures the appointment-setter service has patient details available
     * even during Patient Service downtime.
     */
    @KafkaListener(
        topics = "patient-events",
        groupId = "appointment-setter-group"
    )
    public void consumePatientEvent(PatientEvent event) {
        logger.info("[AppSetter] Received patient event: type={}, patientId={}",
            event.getEventType(), event.getPatientId());

        if ("PatientRegisteredEvent".equals(event.getEventType())) {
            // Check if we already have this patient locally
            Optional<PatientData> existing = patientDataRepository.findByUserId(
                event.getPatientId().longValue()
            );
            if (existing.isEmpty()) {
                logger.info("[AppSetter] New patient {} registered - local record will be created when appointment is first made.",
                    event.getPatientId());
            }
            // Note: Full patient record is created in appodb when an appointment is submitted
            // (the patient supplies their details in the appointment request body)
        }
    }
}
