package com.pecenio.appoms.serviceimpl;

import com.pecenio.appoms.model.AppointmentEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerService {
    private static final Logger logger = LoggerFactory.getLogger(KafkaProducerService.class);
    private static final String TOPIC = "appointment-events";

    @Autowired
    private KafkaTemplate<String, AppointmentEvent> kafkaTemplate;

    public void sendEvent(AppointmentEvent event) {
        logger.info("Publishing event {} to topic {}", event.getEventType(), TOPIC);
        kafkaTemplate.send(TOPIC, String.valueOf(event.getAppointmentId()), event);
    }
}
