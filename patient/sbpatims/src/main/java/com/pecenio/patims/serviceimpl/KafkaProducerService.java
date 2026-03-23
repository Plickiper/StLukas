package com.pecenio.patims.serviceimpl;

import com.pecenio.patims.model.PatientEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerService {
    private static final Logger logger = LoggerFactory.getLogger(KafkaProducerService.class);
    private static final String TOPIC = "patient-events";

    @Autowired
    private KafkaTemplate<String, PatientEvent> kafkaTemplate;

    public void sendEvent(PatientEvent event) {
        logger.info("Publishing event {} to topic {}", event.getEventType(), TOPIC);
        kafkaTemplate.send(TOPIC, String.valueOf(event.getPatientId()), event);
    }
}
