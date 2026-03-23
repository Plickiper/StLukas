package com.pecenio.doctms.serviceimpl;

import com.pecenio.doctms.model.DoctorEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerService {
    private static final Logger logger = LoggerFactory.getLogger(KafkaProducerService.class);
    private static final String TOPIC = "doctor-events";

    @Autowired
    private KafkaTemplate<String, DoctorEvent> kafkaTemplate;

    public void sendEvent(DoctorEvent event) {
        logger.info("Publishing event {} to topic {}", event.getEventType(), TOPIC);
        kafkaTemplate.send(TOPIC, String.valueOf(event.getDoctorId()), event);
    }
}
